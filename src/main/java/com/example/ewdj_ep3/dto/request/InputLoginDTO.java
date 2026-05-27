package com.example.ewdj_ep3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InputLoginDTO(
        @NotBlank
        @Size(min = 4, max = 20)
        String password,

        @NotBlank
        @Email
        String email
) {
    public InputLoginDTO() {
        this(null, null);
    }
}
