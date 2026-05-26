package com.example.ewdj_ep3.validator.registration;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = com.example.ewdj_ep3.validator.registration.PasswordConstraintValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValidPasswords {

    String message() default "password does not match the confirm password";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};

}