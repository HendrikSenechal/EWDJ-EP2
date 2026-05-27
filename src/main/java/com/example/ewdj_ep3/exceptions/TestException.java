package com.example.ewdj_ep3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestException extends RuntimeException {
    private final String testMessage = "Dit is een test exception";
}
