package com.example.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e)
			throws Exception {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", 400);
		body.put("Error", e.getMessage());

		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(body);
	}
}
