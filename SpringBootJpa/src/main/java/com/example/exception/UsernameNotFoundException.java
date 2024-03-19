package com.example.exception;

public class UsernameNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	String message;
	
	public UsernameNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	
}
