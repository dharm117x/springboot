package com.example.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AsyncService;

@RestController
@RequestMapping("/api")
public class AsyncDataController {

	@Autowired
	AsyncService service;
	
	@GetMapping("/testSynch")
	public void testSycnh() throws Exception {
		
		CompletableFuture<String> firstData = service.getFirstData();
		CompletableFuture.allOf(firstData).join();

		return ;
	}
}
