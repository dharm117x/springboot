package com.example.dto;

public class JwtResponse {
	String token;
	String error;

	public JwtResponse() {
	}
	
	public JwtResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
}
