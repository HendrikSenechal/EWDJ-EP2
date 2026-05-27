package com.example.ewdj_ep3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class CustomGenericException extends RuntimeException {

    private String errCode;
    private String errMsg;

}