package com.mytest.maersk.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class NumberFromValidator
        implements ConstraintValidator<NumberFrom, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<Integer> allowedNumbers = Arrays.asList(20, 40);
        if(allowedNumbers.contains(value)) return true;
        else return false;
    }
}