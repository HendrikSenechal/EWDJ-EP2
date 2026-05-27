package com.example.ewdj_ep3.init;

import static com.example.ewdj_ep3.init.InitRegistration.*;
import com.example.ewdj_ep3.dto.request.InputRegistrationDTO;
import lombok.Builder;

@Builder(toBuilder = true)
public class InputRegistrationDTOBuilder {

    @Builder.Default
    private String name = OK_NAME;

    @Builder.Default
    private String lastname = OK_LASTNAME;

    @Builder.Default
    private String password = OK_PASSWORD;

    @Builder.Default
    private String confirmPassword = OK_CONFIRM_PASSWORD;

    @Builder.Default
    private String email = OK_EMAIL;

    public InputRegistrationDTO toDTO() {
        return new InputRegistrationDTO(name, lastname, password, confirmPassword, email);
    }
}
