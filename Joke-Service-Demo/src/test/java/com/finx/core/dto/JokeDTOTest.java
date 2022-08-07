package com.finx.core.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JokeDTOTest {
	private JokeDTO joke;

	@BeforeEach
	public void setUp() {
		this.joke = new JokeDTO();
		this.joke.setCreate_at("26-11-201");
		this.joke.setIcon_url("https://assets.chucknorris.host/img/avatar/chuck-norris.png");
		this.joke.setId("10");
		this.joke.setUrl("https://assets.chucknorris.host/");
		this.joke.setValue("Hot news from the Vatican...white smoke appears. Chuck Norris has been elected Pope! All ye of faith, welcome Pope Charles deNoir-iz Kikyorectumis");
	}
	
	@Test
	void testJokeFullMatchKeyWord(){
		boolean result = this.joke.isFullMatch("news");
		assertEquals(true,result);
	}
	@Test
	void testJokeNotMatchKeyWord(){
		boolean result = this.joke.isFullMatch("ba");
		assertEquals(false,result);
	}
	
}
