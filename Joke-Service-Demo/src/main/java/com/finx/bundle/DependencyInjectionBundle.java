package com.finx.bundle;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.finx.client.DependencyInjectionStrategy;
import com.finx.core.configuration.SingletonConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Environment;

public abstract class DependencyInjectionBundle<T extends Configuration>
		implements ConfiguredBundle<T>, DependencyInjectionStrategy<T> {

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		SingletonConfiguration singletonConfiguration = getDependencyInjectionConfiguration(configuration);
		environment.jersey().register(new AbstractBinder() {
			@Override
			protected void configure() {
				for (Class<?> singletonClass : singletonConfiguration.getSingletons()) {
					bindAsContract(singletonClass).in(Singleton.class);
				}
			}
		});
	}

}
