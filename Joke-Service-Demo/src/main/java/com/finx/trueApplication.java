package com.finx;


import com.finx.api.JokeController;
import com.finx.api.exception.IllegalArgumentExceptionMapper;
import com.finx.bundle.DependencyInjectionBundle;
import com.finx.bundle.JokeAPIBundle;
import com.finx.bundle.RateLimitBundle;
import com.finx.core.configuration.JokeConfiguration;
import com.finx.core.configuration.RateLimitConfiguration;
import com.finx.core.configuration.SingletonConfiguration;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class trueApplication extends Application<FinxConfiguration> {

	public static void main(final String[] args) throws Exception {
		new trueApplication().run(args);
	}
	
	private RateLimitBundle<FinxConfiguration> rateLimitBundle = new RateLimitBundle<FinxConfiguration>() {
		@Override
		public RateLimitConfiguration getRateLimitConfiguration(FinxConfiguration configuration) {
			return configuration.getRateLimit();
		}
	};

	private JokeAPIBundle<FinxConfiguration> jokeAPIBundle = new JokeAPIBundle<FinxConfiguration>() {
		@Override
		public JokeConfiguration getJokeConfiguration(FinxConfiguration configuration) {
			return configuration.getJokeApi();
		}
	};
	
	private DependencyInjectionBundle<FinxConfiguration> denpendencyBundle = new DependencyInjectionBundle<FinxConfiguration>() {

		@Override
		public SingletonConfiguration getDependencyInjectionConfiguration(FinxConfiguration configuration) {			
			return configuration.getSingletonConfiguration();
		}
		
	};


	@Override
	public String getName() {
		return "true";
	}

	@Override
	public void initialize(final Bootstrap<FinxConfiguration> bootstrap) {
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
				
		bootstrap.addBundle(rateLimitBundle);
		bootstrap.addBundle(jokeAPIBundle);
		bootstrap.addBundle(denpendencyBundle);

	}

	@Override
    public void run(final FinxConfiguration configuration,
                    final Environment environment) {
		environment.jersey().register(new IllegalArgumentExceptionMapper(environment.metrics()));
        environment.jersey().register(JokeController.class);    
	}
        
}
