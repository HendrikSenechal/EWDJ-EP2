package com.example.ewdj_ep3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MatchNotFoundException extends RuntimeException {
    private final Integer id;
}