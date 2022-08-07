package com.finx.client;

import com.finx.core.configuration.SingletonConfiguration;

import io.dropwizard.Configuration;

public interface DependencyInjectionStrategy <T extends Configuration>{
	SingletonConfiguration getDependencyInjectionConfiguration (T configuration);

}
