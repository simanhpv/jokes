package com.finx.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.finx.core.dto.JokeDTO;
import com.finx.core.ratelimit.RateLimited;
import com.finx.core.service.JokeService;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;




@Path(value="/v1/api/")
public class JokeController {
	
private Logger LOGGER = Logger.getLogger(JokeService.class);
private JokeService jokeService;
	
@GET()
@Path(value="joke")
@Produces(MediaType.APPLICATION_JSON)
@RateLimited
public List<JokeDTO> getJoke(@QueryParam("keyword") String keyword) throws JsonProcessingException {
    LOGGER.info("start consume request with keyword : " + keyword);
	return jokeService.searchJoke(keyword);
    
}

@Inject
public JokeController(JokeService jokeService) {
	this.jokeService = jokeService;
}

public JokeService getJokeService() {
	return jokeService;
}

public void setJokeService(JokeService jokeService) {
	this.jokeService = jokeService;
}

}
