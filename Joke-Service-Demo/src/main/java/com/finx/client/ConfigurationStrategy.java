package com.finx.client;

import com.finx.core.configuration.RateLimitConfiguration;

import io.dropwizard.Configuration;

public interface ConfigurationStrategy<T extends Configuration> {
    RateLimitConfiguration getRateLimitConfiguration(T configuration);
}
