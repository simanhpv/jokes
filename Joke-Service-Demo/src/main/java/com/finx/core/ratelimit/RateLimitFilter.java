package com.finx.core.ratelimit;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.finx.core.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@RateLimited
public class RateLimitFilter implements ContainerRequestFilter {
    Logger LOGGER = Logger.getLogger("RateLimitFilter");
    private JedisPool jedisPool;

    @Context
    private HttpServletRequest request;

    public RateLimitFilter(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        // check if client exceeded rate limit
        if (isOverRateLimit())
        {
            requestContext.abortWith(Response.status(429).entity("Too Many Requests with the same keyword").build());
        }
    }

    // testable method to do the rate limit check
    private boolean isOverRateLimit() {
        String requestIdentifier = getRequestKeyword();       
        if (requestIdentifier == null) {
            return false;
        }
        Jedis jedis = jedisPool.getResource();
       
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(requestIdentifier);
        ArrayList<String> params = new ArrayList<String>();
        params.add("[[1, 10], [3, 200], [36, 300]]");
        Long unixTime = System.currentTimeMillis() / 1000L;        
        params.add(unixTime.toString());
        params.add("60");

        long result = (long)jedis.evalsha("08b03d80e639c5b33de9df638ca4fecbd727b1d0", keys, params);
       jedisPool.returnResource(jedis);
        if (result != 0) {
            return true;
        } else {
            return false;
        }

    }
    
    private String getRequestKeyword() {
        if (request.getParameter(Constants.KEYWORD) != null) {
            return "keyword:"+ request.getParameter(Constants.KEYWORD);
        }
        return null;
    }

}
