package com.example.ewdj_ep3.advice;

import com.example.ewdj_ep3.domain.user.UserController;
import com.example.ewdj_ep3.validator.registration.RegistrationValidator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import lombok.RequiredArgsConstructor;

//pas enkel toe voor usercontroller
@ControllerAdvice(assignableTypes = UserController.class)
//constructor injectie voor registrationValidator
@RequiredArgsConstructor
public class RegistrationValidatorAdvice {

    private final RegistrationValidator registrationValidator;

    // Dit vertelt spring om iedere keer er een inputRegistrationDTO gebind wordt aan een form deze validator mee te geven
    @InitBinder("inputRegistrationDTO")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(registrationValidator);
    }
}

