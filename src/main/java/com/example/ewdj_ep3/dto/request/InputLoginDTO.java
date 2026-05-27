package com.example.ewdj_ep3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InputLoginDTO(
        @NotBlank(message = "{login.password.notblank}")
        @Size(min = 4, max = 20, message = "{login.password.size}")
        String password,

        @NotBlank(message = "{login.email.notblank}")
        @Email(message = "{login.email.invalid}")
        String email
) {
    public InputLoginDTO() {
        this(null, null);
    }
}