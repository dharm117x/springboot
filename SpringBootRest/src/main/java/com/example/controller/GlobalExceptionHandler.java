package com.example.controller;

import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.data.ErrorDetail;
import com.example.data.ErrorResponse;
import com.example.service.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetail errors = new ErrorDetail();
		errors.setTime(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errors);
	}

}
