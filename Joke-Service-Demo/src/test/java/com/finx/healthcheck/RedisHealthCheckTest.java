package com.finx.healthcheck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import com.codahale.metrics.health.HealthCheck.Result;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@ExtendWith(DropwizardExtensionsSupport.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RedisHealthCheckTest {
	
	@Mock
	private Jedis jedis = mock(Jedis.class);
	@Mock
	private JedisPool jedisPool     = mock(JedisPool.class);
	
	private RedisHealthCheck redisHealthCheck = new RedisHealthCheck(jedisPool);
	
 @BeforeAll
 public void setUp() {
     reset(jedisPool);
  }
 
 @Test
 public void shouldReturnHealthyIfPingSucceeds() throws Exception {
     when(jedisPool.getResource()).thenReturn(jedis);
     when(jedis.ping()).thenReturn("PONG");
     
     assertEquals(redisHealthCheck.check(), Result.healthy());
     
 }
 
 @Test
 public void shouldReturnFailed() throws Exception {
     when(jedisPool.getResource()).thenReturn(jedis);
     when(jedis.ping()).thenReturn("dddd");     
     assertNotEquals(redisHealthCheck.check(), Result.healthy());
     
 }
	

}
