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
	public String testSycnh() throws Exception {
		
		CompletableFuture<String> firstData1 = service.getFirstData(100);
		CompletableFuture<String> firstData2 = service.getFirstData(2);
		CompletableFuture<String> firstData3 = service.getFirstData(3);
		CompletableFuture<String> firstData4 = service.getFirstData(4);
		System.out.println("Startting.......");
		
		Object data = CompletableFuture.anyOf(firstData1, firstData2, firstData3, firstData4).join();
		System.out.println("Completed.");
		
		
//		firstData1.get()+"--"+ firstData2.get()+"--"+firstData3.get()+"--"+firstData4.get()
		return data.toString();
	}
}
