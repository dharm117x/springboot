package com.example.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValiator implements ConstraintValidator<EmailValiation, String>
{
	final String emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
	final Pattern pattern = Pattern.compile(emailRegex);

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.out.println("EmailValiator.isValid()");
		return pattern.matcher(value).matches();
	}
}