package com.example.validator;

import java.util.Arrays;
import java.util.List;
 
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
 
public class CommPreferenceValidator implements ConstraintValidator<CommPreference, String> {
 
    private final List<String> commPreferences  = Arrays.asList("email", "mobilePhone");
     
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return commPreferences.contains(value);
    }
 
}