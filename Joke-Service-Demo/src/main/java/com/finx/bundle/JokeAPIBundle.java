package com.finx.bundle;

import com.finx.core.configuration.JokeConfiguration;
import com.finx.core.repository.JokeRepository;
import com.finx.healthcheck.JokeExternalAPIHealthCheck;
import com.finx.healthcheck.RedisHealthCheck;
import com.finx.client.JokeAPIConfigurationStrategy;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Environment;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public abstract class JokeAPIBundle<T extends Configuration>
		implements ConfiguredBundle<T>, JokeAPIConfigurationStrategy<T> {

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		JokeConfiguration config = getJokeConfiguration(configuration);
		HttpUrl url = new HttpUrl.Builder().scheme("https").host(config.getHost()).addPathSegment(config.getEndPoint())
				.addQueryParameter("query", "football").build();
		OkHttpClient client = new OkHttpClient();

		environment.healthChecks().register("jokeAPI", new JokeExternalAPIHealthCheck(client, url));
		JokeRepository jokeExternalAPI = new JokeRepository();
		jokeExternalAPI.setApiConfig(config);
		environment.jersey().register(jokeExternalAPI);

	}

}
