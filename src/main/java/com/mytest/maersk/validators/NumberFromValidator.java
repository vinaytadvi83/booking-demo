package com.mytest.maersk.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class NumberFromValidator
        implements ConstraintValidator<NumberFrom, Integer> {

    Integer [] allowedValues;

    @Override
    public void initialize(NumberFrom constraintAnnotation) {
        int [] values = constraintAnnotation.values();
        allowedValues = IntStream.of(values).boxed().toArray(Integer []::new);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        List<Integer> allowedNumbers = Arrays.asList(20, 40);
        return allowedNumbers.contains(value);
    }
}