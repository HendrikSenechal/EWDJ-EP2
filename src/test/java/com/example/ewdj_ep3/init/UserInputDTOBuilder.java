package com.example.ewdj_ep3.init;

import static com.example.ewdj_ep3.init.InitRegistration.*;
import com.example.ewdj_ep3.dto.request.UserInputDTO;
import lombok.Builder;

@Builder(toBuilder = true)
public class UserInputDTOBuilder {

    @Builder.Default
    private String name = OK_FIRSTNAME;

    @Builder.Default
    private String lastName = OK_LASTNAME;

    @Builder.Default
    private String password = OK_PASSWORD;

    @Builder.Default
    private String confirmPassword = OK_CONFIRM_PASSWORD;

    @Builder.Default
    private String email = OK_EMAIL;

    public UserInputDTO toDTO() {
        return new UserInputDTO(name, lastName, password, confirmPassword, email);
    }
}
