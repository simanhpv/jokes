package com.finx.core.repository;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.finx.FinXApplication;
import com.finx.FinxConfiguration;
import com.finx.core.configuration.JokeConfiguration;
import com.finx.core.response.Response;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@Singleton
@ExtendWith(DropwizardExtensionsSupport.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JokeRepoTest {
		
    public static final DropwizardTestSupport<FinxConfiguration> SUPPORT =
	            new DropwizardTestSupport<FinxConfiguration>(FinXApplication.class,
	                ResourceHelpers.resourceFilePath("application-test.yml"),
	                ConfigOverride.config("server.applicationConnectors[0].port", "0"));

  @BeforeAll 
    public void beforeClass() throws Exception {
        SUPPORT.before();
    }

    @AfterAll
    public void afterClass() {
        SUPPORT.after();
    }
	
@Test
 void searchByKeyword() throws StreamReadException, DatabindException, IOException {
	ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("fixtures/result.json").getFile());
    String jsonReurn = FileUtils.readFileToString(file, "UTF-8");
    
    JokeRepository jokeRepository = new JokeRepository();
    jokeRepository.setApiConfig(SUPPORT.getConfiguration().getJokeApi());
    Client client = ClientBuilder.newClient();
    JokeConfiguration apiConfig = jokeRepository.getApiConfig();
    String url = apiConfig.getBaseUrl() + "" + apiConfig.getEndPoint();
	String api = url + "?query=abd";
    Invocation.Builder request = client.target(api).request();  
	Response response = jokeRepository.getJokeByKeyword("abd");
	assertEquals(7, response.getResult().size());
	
}

}
