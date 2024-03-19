package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClinetService {

	private final WebClient webClient;

	public WebClinetService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://localhost:9001").build();
	}

	public WebClient getWebClient() {
		return webClient;
	}
	
	
}
