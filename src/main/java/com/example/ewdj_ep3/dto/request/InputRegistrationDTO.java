package com.example.ewdj_ep3.dto.request;

import com.example.ewdj_ep3.validator.registration.ValidPasswords;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidPasswords
public record InputRegistrationDTO(
        //@Builder.Default ensures default values when not set by builder
        @NotBlank(message = "firstname is required")
        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "firstname may only contain letters"
        )
        @Size(min = 2, max = 30)
        String name,

        @NotBlank(message = "lastname is required")
        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "lastname may only contain letters"
        )
        @Size(min = 2, max = 30)
        String lastname,

        @NotBlank
        @Size(min = 4, max = 20)
        String password,

        @NotBlank
        @Size(min = 4, max = 20)
        String confirmPassword,

        @NotBlank
        @Email
        String email
) {

    public InputRegistrationDTO() {
        this(null, null, null, null, null);
    }
}