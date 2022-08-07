package com.finx;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finx.core.configuration.JokeConfiguration;
import com.finx.core.configuration.RateLimitConfiguration;
import com.finx.core.configuration.SingletonConfiguration;

import javax.validation.constraints.*;

public class FinxConfiguration extends Configuration {
	
    private String appName;   
    @NotNull
	@JsonProperty
	private RateLimitConfiguration rateLimit;
    
    @NotNull
	@JsonProperty
	private JokeConfiguration  jokeApi;


	public RateLimitConfiguration getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(RateLimitConfiguration rateLimit) {
		this.rateLimit = rateLimit;
	}
    
    public FinxConfiguration(){

    }
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

	public JokeConfiguration getJokeApi() {
		return jokeApi;
	}
	public void setJokeApi(JokeConfiguration jokeApi) {
		this.jokeApi = jokeApi;
	}
	
	public SingletonConfiguration getSingletonConfiguration() {
		return new SingletonConfiguration();
	}
    
}
