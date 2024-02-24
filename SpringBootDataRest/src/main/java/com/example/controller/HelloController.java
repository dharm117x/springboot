package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/ping")
	public String ping() {
		System.out.println("HelloController.ping()");
		return "Welcome";
	}
}
