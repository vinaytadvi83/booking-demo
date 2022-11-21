package com.maersk.booking.validator;

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

    int [] values();
    String message() default "Provided numbers not allowed!" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}