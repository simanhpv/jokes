package com.finx.core.configuration;

import java.util.ArrayList;
import java.util.List;

import com.finx.api.JokeController;
import com.finx.core.repository.JokeRepository;
import com.finx.core.service.JokeService;

public class SingletonConfiguration {

	public List<Class<?>> getSingletons() {		
		 final List<Class<?>> result = new ArrayList<>();
	        result.add(JokeRepository.class);
	        result.add(JokeService.class);
	        result.add(JokeController.class);	       
	        return result;
	}
}
