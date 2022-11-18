package com.mytest.maersk.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeFormatValidator
        implements ConstraintValidator<NumberFrom, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        DateFormat sdf = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss'Z'");
        sdf.setLenient(false);
        try {
            sdf.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}