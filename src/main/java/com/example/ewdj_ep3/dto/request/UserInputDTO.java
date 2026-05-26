package com.example.ewdj_ep3.dto.request;

public record UserInputDTO(
        String name,
        String lastname,
        String email,
        String password
) {

    public UserInputDTO() {
        this(null, null, null, null);
    }
}