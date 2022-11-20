package com.mytest.maersk.validator;

import com.mytest.maersk.model.ContainerType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContainerTypeValidator.class)
public @interface ContainerTypes {

    String message() default "Provided values not allowed!" ;

    ContainerType [] anyOf();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}