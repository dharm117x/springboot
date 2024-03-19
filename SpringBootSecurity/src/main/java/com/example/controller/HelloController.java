package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.LoginService;

@RestController
public class HelloController {

	@Autowired
	LoginService loginService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello Security world..........";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
		System.out.println(username+"------"+password);
		return loginService.login(username, password)?"sucess":"failed";
	}
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String user() {
		
		return "Hello User world 1..........";
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		
		return "Hello Aadmin world 1..........";
	}
	
	
}
