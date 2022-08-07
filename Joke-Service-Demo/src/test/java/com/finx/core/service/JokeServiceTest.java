package com.finx.core.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finx.core.repository.JokeRepository;
import com.finx.core.response.Response;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
@TestInstance(Lifecycle.PER_CLASS)
public class JokeServiceTest {

	@Mock
	private JokeRepository jokeRepository = mock(JokeRepository.class);
	
	private Response response;
	private JokeService jokeService = new JokeService(jokeRepository);

	@BeforeAll
	public void setUp() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("fixtures/result.json").getFile());
		String jsonReurn = FileUtils.readFileToString(file, "UTF-8");
		ObjectMapper mapper = new ObjectMapper();
		response = mapper.readValue(jsonReurn, Response.class);
	}

	@Test
	void searchByKeywordHasResult() throws StreamReadException, DatabindException, IOException {
		when(jokeRepository.getJokeByKeyword("spotted")).thenReturn(response);
		assertEquals(1, jokeService.searchJoke("spotted").size());
		
	}
	@Test
	void searchByKeywordNoResult() throws StreamReadException, DatabindException, IOException {
		when(jokeRepository.getJokeByKeyword("abc")).thenReturn(response);
		assertEquals(0, jokeService.searchJoke("abc").size());
		
	}

}
