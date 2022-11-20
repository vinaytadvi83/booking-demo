package com.mytest.maersk.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
 * Validate If allowed Date format is supplied
 * Allowed format is ISO-8601 date and time for UTC timezone
 */
public class DateTimeFormatValidator
        implements ConstraintValidator<ValidDateTimeFormat, String> {

    public static String ISO_8601BASIC_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        DateFormat sdf = new SimpleDateFormat(ISO_8601BASIC_DATE_PATTERN);
        sdf.setLenient(false);
        try {
            sdf.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}