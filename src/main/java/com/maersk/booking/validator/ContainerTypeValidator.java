package com.maersk.booking.validator;

import com.maersk.booking.model.ContainerType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/*
 * Validate if Enum values provided are allowed
 */
public class ContainerTypeValidator
        implements ConstraintValidator<ContainerTypes, ContainerType> {

    ContainerType [] allowedValues;

    @Override
    public void initialize(ContainerTypes constraintAnnotation) {
        this.allowedValues = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(ContainerType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(allowedValues).contains(value);
    }
}