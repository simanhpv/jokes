package com.finx.core.configuration;

public class JokeConfiguration {
	private String baseUrl;
	private String endPoint;	
	private String host;
	
	public JokeConfiguration() {
		
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
