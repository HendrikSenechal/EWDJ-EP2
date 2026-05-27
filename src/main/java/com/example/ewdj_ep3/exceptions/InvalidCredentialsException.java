package com.example.ewdj_ep3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidCredentialsException extends RuntimeException {
    private final String email;
    private String errCode;
    private final String errMsg;
}