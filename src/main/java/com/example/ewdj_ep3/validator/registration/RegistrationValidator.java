package com.example.ewdj_ep3.validator.registration;

import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    //Definieert welke objecten de validator kan valideren. In dit geval is dat InputRegistrationDTO
    @Override
    public boolean supports(Class<?> klass) {
        return InputRegistrationDTO.class.isAssignableFrom(klass);
    }

    // Methode voor validatie met target en errors waar validatie errors opgeslagen worden
    @Override
    public void validate(Object target, Errors errors) {
        //casten
        InputRegistrationDTO dto = (InputRegistrationDTO) target;

        //checken of wachtwoord null is, indien ja stop dan de validatie
        if (dto.password() == null || dto.confirmPassword() == null) {
            return;
        }

        // checken of wachtwoorden gelijk zijn. Indien niet error teruggeven
        if (!(dto.password()).equals(dto.confirmPassword())) {
            errors.rejectValue("password",
                    "matchingPassword.registration.password",
                    "password does not match the confirm password.");
        }
    }
}
