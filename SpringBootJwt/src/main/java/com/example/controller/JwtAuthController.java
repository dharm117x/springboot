package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.JwtTokenUtility;
import com.example.config.JwtUserDetailsService;
import com.example.dto.JwtRequest;
import com.example.dto.JwtResponse;

@RestController
public class JwtAuthController {

	@Autowired
	JwtUserDetailsService service;
	@Autowired
	AuthenticationManager manager;
	@Autowired
	JwtTokenUtility utility;
	
	@PostMapping(path= "/auth" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public JwtResponse authenticate(@RequestBody JwtRequest req) {
		
		JwtResponse res = new JwtResponse();
		
		try {
			manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		} catch (Exception e) {
			res.setError("Credential not valid");
			return res;
		}
		
		String token = utility.genrateToken(req.getUsername());
		res.setToken(token);
		return res;
	}
}
