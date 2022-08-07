package com.finx.core.repository;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finx.core.configuration.JokeConfiguration;
import com.finx.core.response.Response;
import com.finx.core.utils.RestUtils;

public class JokeRepository {
	private static Logger LOGGER = Logger.getLogger("JokeExternalAPI");
	private JokeConfiguration apiConfig;

	public JokeRepository() {

	}
	
	public Response getJokeByKeyword(String keyword) {
		LOGGER.info("Start calling API wit keyword: " + keyword);
		String url = apiConfig.getBaseUrl() + "" + apiConfig.getEndPoint();
		String api = url + "?query=" + keyword;
		LOGGER.info("api = " + api);
		Response response = null;
		try {
			Client client = ClientBuilder.newClient();
			Builder builder = client.target(api).request();
			String jsonVal = builder.get(String.class);
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.readValue(jsonVal, Response.class);
		} catch (ProcessingException e) {
			LOGGER.error("Call external AIP error " + e);
			throw new RuntimeException(e);
		} catch (WebApplicationException e1) {
			LOGGER.error("Call external AIP error " + e1);
			throw new RuntimeException(e1);
		} catch (JsonProcessingException e2) {
			LOGGER.error("parsed result error " + e2);
			throw new RuntimeException(e2);
		}
		return response;
	}

	public JokeConfiguration getApiConfig() {
		return apiConfig;
	}

	public void setApiConfig(JokeConfiguration apiConfig) {
		this.apiConfig = apiConfig;
	}

}
