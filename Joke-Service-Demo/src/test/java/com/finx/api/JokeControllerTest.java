package com.finx.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import com.finx.FinXApplication;
import com.finx.FinxConfiguration;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JokeControllerTest {

	private static DropwizardAppExtension<FinxConfiguration> EXT = new DropwizardAppExtension<>(FinXApplication.class,
			ResourceHelpers.resourceFilePath("application-test.yml"));

	@Test
	void loginHandlerRedirectsAfterPost() {
		Client client = EXT.client();
		Response response = client.target(String.format("http://localhost:%d/v1/api/joke?keyword=abcd", EXT.getLocalPort())).request()
				.get();
		assertEquals(200, response.getStatus());	
	}
}
