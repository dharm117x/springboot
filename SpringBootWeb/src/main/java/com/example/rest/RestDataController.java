package com.example.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestDataController {

	@GetMapping("/data")
	public String getData(@RequestParam String name) {
		
		return "Hello Rest world --> "+name;
	}
}
