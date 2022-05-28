package com.example.controller;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.AesSecurity;
import com.example.service.UserService;

@RestController
//@CrossOrigin(origins = "*")
public class TokenController {

	@Autowired UserService service;
	
	@Autowired AesSecurity security;
	
	@PostMapping("/token")
	public String getToken(@RequestParam String username, @RequestParam String password) throws Exception {
		String token = service.login(username, password);
		if(StringUtils.isEmpty(token)) {
			return "Token: Token Not found";
		}
		Key aesKey = security.getAESKey();
		String encrypt = security.encrypt(token, aesKey);
		System.out.println("EC :: "+encrypt);
		System.out.println("DC :: "+ security.decrypt(encrypt, aesKey));
		
		return "Token :: "+ token;
	}
}
