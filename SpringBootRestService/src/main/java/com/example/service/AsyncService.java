package com.example.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AsyncService {

	@Autowired
	RestTemplate restTemplate;
	
	@Async(value = "asynchExecutor")
	public CompletableFuture<String> getFirstData() throws Exception {
		String data1 = restTemplate.getForObject("https://localhost:9091/users", String.class);
		System.out.println(data1);
		Thread.sleep(1000L);
		
		return CompletableFuture.completedFuture(data1);
	}
}
