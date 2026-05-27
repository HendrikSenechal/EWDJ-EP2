package com.example.ewdj_ep3.validator.registration;

import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPasswords, InputRegistrationDTO> {

    @Override
    public void initialize(ValidPasswords constraintAnnotation) {
    }

    @Override
    public boolean isValid(InputRegistrationDTO dto, ConstraintValidatorContext context) {

        if (dto.password() == null ||
                dto.confirmPassword() == null) {
            return true;
        }

        boolean isValid = dto.password().equals(dto.confirmPassword());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("password")
                    .addConstraintViolation();
        }

        return isValid;
    }
}