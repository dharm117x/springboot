package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.data.ErrorDetail;
import com.example.data.ErrorResponse;

public class BaseController {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ErrorResponse methodException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		ErrorResponse res = validationMessage(result);
		res.setMessage("Error count:: "+ex.getErrorCount());
		return res;
	}

	/*
	 * @ExceptionHandler({UserNotFoundException.class}) public ErrorResponse
	 * userNotfoundError(BindException ex) { BindingResult result =
	 * ex.getBindingResult(); ErrorResponse res = validationMessage(result);
	 * res.setStatus("Error count:: "+ex.getErrorCount()); return res; }
	 */

	private ErrorResponse validationMessage(BindingResult result) {
		ErrorResponse res = new ErrorResponse();
		List<ErrorDetail> errList = new ArrayList<>();
		result.getFieldErrors().forEach((error)->{
			ErrorDetail ed = new ErrorDetail();
			ed.setField(error.getField());
			ed.setMessage(error.getDefaultMessage());
			errList.add(ed);
		});
		
		res.setErrors(errList);
		return res;
	}

}
