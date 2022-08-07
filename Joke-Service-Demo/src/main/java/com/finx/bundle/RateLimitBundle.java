package com.finx.bundle;

import com.finx.client.ConfigurationStrategy;
import com.finx.core.configuration.RateLimitConfiguration;
import com.finx.core.ratelimit.RateLimitFilter;
import com.finx.healthcheck.RedisHealthCheck;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URL;

public abstract class RateLimitBundle<T extends Configuration> implements ConfiguredBundle<T>, ConfigurationStrategy<T> {
	private JedisPoolConfig jedisPoolConfig;
	private JedisPool jedisPool;

	// to inject mock JedisPool for testing
	public void run(T configuration, Environment environment, JedisPool jedisPool) throws Exception {
		this.jedisPool = jedisPool;
		run(configuration, environment);
	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {

		environment.lifecycle().manage(new Managed() {
			@Override
			public void start() throws Exception {
			}

			@Override
			public void stop() throws Exception {
				jedisPool.destroy();
			}
		});
		
		// load redis config, setup redis connection pool
		RateLimitConfiguration conf = getRateLimitConfiguration(configuration);
		jedisPoolConfig = conf.getPoolConfig();
		if (jedisPool == null) {
			jedisPool = new JedisPool(jedisPoolConfig, conf.getRedisHost(), conf.getRedisPort());
		}
		environment.healthChecks().register("redis", new RedisHealthCheck(jedisPool));

		// load redis lua script into script cache
		URL url = Resources.getResource("SlidingWindowRateLimit.lua");
		String slidingWindowRateLimitLua = Resources.toString(url, Charsets.UTF_8);
		Jedis jedis = jedisPool.getResource();
		String sha1 = jedis.scriptLoad(slidingWindowRateLimitLua);		
		jedisPool.returnResource(jedis);
		//jedis.close();
		// register the filter
		RateLimitFilter rateLimitFilter = new RateLimitFilter(jedisPool);
		environment.jersey().register(rateLimitFilter);
	}

	@Override
	public void initialize(Bootstrap<?> bootstrap) {
	}
}
