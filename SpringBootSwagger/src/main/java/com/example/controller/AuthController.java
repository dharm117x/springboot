package com.example.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.util.CommonUtils;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	@PostMapping("/auth")
	public String aunthentication(HttpServletRequest req) {
		System.out.println("Session ID : : " + req.getSession(false));
		String token = UUID.randomUUID().toString();
		
		CommonUtils.updateToken(token);
		
		return "Token ::  "+ token;
	}
}
