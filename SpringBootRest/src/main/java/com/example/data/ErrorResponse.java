package com.example.data;

import java.util.List;

public class ErrorResponse {
	List<ErrorDetail> errors;
	String message;

	public List<ErrorDetail> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorDetail> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
