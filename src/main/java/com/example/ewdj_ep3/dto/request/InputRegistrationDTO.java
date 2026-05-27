package com.example.ewdj_ep3.dto.request;

import com.example.ewdj_ep3.validator.registration.ValidPasswords;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@ValidPasswords
public record InputRegistrationDTO(

        @NotBlank(message = "{register.firstname.notblank}")
        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "{register.firstname.pattern}"
        )
        @Size(min = 2, max = 30, message = "{register.firstname.size}")
        String name,

        @NotBlank(message = "{register.lastname.notblank}")
        @Pattern(
                regexp = "^[a-zA-Z]+$",
                message = "{register.lastname.pattern}"
        )
        @Size(min = 2, max = 30, message = "{register.lastname.size}")
        String lastname,

        @NotBlank(message = "{register.password.notblank}")
        @Size(min = 4, max = 20, message = "{register.password.size}")
        String password,

        @NotBlank(message = "{register.confirmPassword.notblank}")
        @Size(min = 4, max = 20, message = "{register.confirmPassword.size}")
        String confirmPassword,

        @NotBlank(message = "{register.email.notblank}")
        @Email(message = "{register.email.invalid}")
        String email
) {

        public InputRegistrationDTO() {
                this(null, null, null, null, null);
        }
}