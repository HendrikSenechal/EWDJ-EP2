package com.example.ewdj_ep3.dto.request;

import org.junit.jupiter.api.BeforeEach;

import static com.example.ewdj_ep3.init.InitRegistration.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Set;
import java.util.stream.Stream;

import com.example.ewdj_ep3.init.InputRegistrationDTOBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class InputRegistrationDTOTest {

    private Validator validator;

    //Validator checks if Java objects comply with constraints specified by annotations like @NotBlank, @ValidEmail, ...
    //It performs the validation and returns any violations found.
    @BeforeEach
    void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static Stream<Arguments> validRegistrationData() {
        return Stream.of(Arguments.of("abcd", "abcd", OK_EMAIL),
                Arguments.of("abcdeabcdeabcde", "abcdeabcdeabcde", OK_EMAIL),
                Arguments.of(OK_NAME, OK_LASTNAME, OK_EMAIL),
                Arguments.of( OK_NAME, OK_LASTNAME, "a@b"));
    }

    @ParameterizedTest
    @MethodSource("validRegistrationData")
    void testValidRegistration(String name, String lastname, String email) {
        InputRegistrationDTO validRegistration = InputRegistrationDTOBuilder.builder().name(name).lastname(lastname).email(email).build().toDTO();

        Set<ConstraintViolation<InputRegistrationDTO>> violations = validator.validate(validRegistration);
        assertThat(violations).isEmpty();
    }

    private static Stream<Arguments> invalidRegistrationData() {
        return Stream.of(Arguments.of("!@#", OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "name"),
                Arguments.of("!@#!@#%!$@#", OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "name"),
                Arguments.of("ab1c", OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "name"),
                Arguments.of("1abcdefg", OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "name"),
                Arguments.of("", OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "name"),
                Arguments.of(OK_NAME, "!@#", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "lastname"),
                Arguments.of(OK_NAME, "!@#!@#%!$@#", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "lastname"),
                Arguments.of(OK_NAME, "ab1c", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "lastname"),
                Arguments.of(OK_NAME, "1abcdefg", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "lastname"),
                Arguments.of(OK_NAME, "", OK_PASSWORD, OK_CONFIRM_PASSWORD, OK_EMAIL, "lastname"),

                Arguments.of(OK_NAME, OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, "test", "email"),

                Arguments.of(OK_NAME, OK_LASTNAME, OK_PASSWORD, OK_CONFIRM_PASSWORD, "", "email"),

                Arguments.of(OK_NAME, OK_LASTNAME, "123", "123", OK_EMAIL, "password"),
                Arguments.of(OK_NAME, OK_LASTNAME, "123456789012345678901", "123456789012345678901", OK_EMAIL, "password"),
                Arguments.of(OK_NAME, OK_LASTNAME, "", "1234", OK_EMAIL, "password"),
                Arguments.of(OK_NAME, OK_LASTNAME, "   ", "1234", OK_EMAIL, "password"),

                //@ValidPasswords
                //Arguments.of(OK_USERNAME, OK_PASSWORD, OK_PASSWORD+"1", OK_EMAIL, "password"),

                Arguments.of(OK_NAME, OK_LASTNAME, "1234", "", OK_EMAIL, "confirmPassword"),
                Arguments.of(OK_NAME, OK_LASTNAME, "1234", "   ", OK_EMAIL, "confirmPassword")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRegistrationData")
    void testInvalidRegistration(String name, String lastname, String password, String confirmPassword, String email, String expected) {

        InputRegistrationDTO invalidRegistration =  InputRegistrationDTOBuilder.builder()
                .name(name).lastname(lastname).email(email).password(password).confirmPassword(confirmPassword)
                .build()
                .toDTO();

        Set<ConstraintViolation<InputRegistrationDTO>> violations = validator.validate(invalidRegistration);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals(expected));
    }


}
