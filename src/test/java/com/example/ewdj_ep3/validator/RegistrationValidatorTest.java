package com.example.ewdj_ep3.validator;

import static com.example.ewdj_ep3.init.InitRegistration.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import com.example.ewdj_ep3.init.InputRegistrationDTOBuilder;
import com.example.ewdj_ep3.validator.registration.RegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class RegistrationValidatorTest {

    private Validator registrationValidator;

    @BeforeEach
    void beforeEach() {
        registrationValidator = new RegistrationValidator();
    }

    @Test
    void testValidPasswords() {
        InputRegistrationDTO validRegistration = InputRegistrationDTOBuilder.builder().password("abcdef").confirmPassword("abcdef").build().toDTO();
        Errors errors = new BeanPropertyBindingResult(validRegistration, "inputRegistrationDTO");
        registrationValidator.validate(validRegistration, errors);
        assertThat(errors.getAllErrors()).isEmpty();
    }

    @Test
    void testInvalidPasswords()
    {
        InputRegistrationDTO invalidRegistration = InputRegistrationDTOBuilder.builder().confirmPassword(OK_CONFIRM_PASSWORD+"1").build().toDTO();
        Errors errors = new BeanPropertyBindingResult(invalidRegistration, "inputRegistrationDTO");
        registrationValidator.validate(invalidRegistration, errors);
        assertThat(errors.getAllErrors()).isNotEmpty();
        assertThat(errors.getErrorCount()).isEqualTo(1);
        assertThat(errors.getFieldError("password")).isNotNull();
    }
}
