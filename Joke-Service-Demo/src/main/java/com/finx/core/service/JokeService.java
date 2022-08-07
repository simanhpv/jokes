package com.finx.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.RepaintManager;

import org.jboss.logging.Logger;

import com.finx.core.dto.JokeDTO;
import com.finx.core.repository.JokeRepository;
import com.finx.core.response.Response;

@Singleton
public class JokeService {
	private Logger LOGGER = Logger.getLogger(JokeService.class);
	
	private JokeRepository jokeExternalAPI;
	
	@Inject
	public JokeService(JokeRepository jokeExternalAPI) {
		this.jokeExternalAPI = jokeExternalAPI;
	}

	public JokeRepository getJokeExternalAPI() {
		return jokeExternalAPI;
	}

	public void setJokeExternalAPI(JokeRepository jokeExternalAPI) {
		this.jokeExternalAPI = jokeExternalAPI;
	}
	public List<JokeDTO> searchJoke(String keyword){		
		LOGGER.info("get joke with keyword : " + keyword);
		return filterJokeFullMatch(jokeExternalAPI.getJokeByKeyword(keyword), keyword);
	}
	
	private List<JokeDTO> filterJokeFullMatch(Response response, String keyword) {
		
		List<JokeDTO> result = new ArrayList<>();	
		if(null != response.getResult() || !response.getResult().isEmpty()) {
			LOGGER.info("start filter by keyword");
			List<JokeDTO> responseJoke = response.getResult();
			for(JokeDTO jokeDto : responseJoke) {
				if(jokeDto.isFullMatch(keyword)) {
					result.add(jokeDto);
				}
			}
			LOGGER.info("End of filter joke...");
		}
		return result;
	}

	

}
