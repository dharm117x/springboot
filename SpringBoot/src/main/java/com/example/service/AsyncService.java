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
	public CompletableFuture<String> getFirstData(Integer data) throws Exception {
		String user = restTemplate.getForObject("http://localhost:8080/api/users", String.class);
		System.out.println(user);
		Thread.sleep(data);
		
		return CompletableFuture.completedFuture(user + " => "+data);
	}
	
	
}
