package com.mytest.maersk.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberFromValidator.class)
public @interface NumberFrom {
    String message() default "Allowed numbers are 20 and 40";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}