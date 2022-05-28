package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtTokenUtility;
import com.example.config.JwtUserDetailsService;
import com.example.dto.JwtRequest;

@RestController
public class JwtAuthController {

	@Autowired
	JwtUserDetailsService service;
	@Autowired
	AuthenticationManager manager;
	@Autowired
	JwtTokenUtility utility;
	
	@PostMapping("/auth")
	public ResponseEntity<?> authenticate(@RequestBody JwtRequest req) {
		
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credential not valid");
		}
		
		Object token = utility.genrateToken(req.getUsername());
		return ResponseEntity.ok("Token: "+token);
	}
}
