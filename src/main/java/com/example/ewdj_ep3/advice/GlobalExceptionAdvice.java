package com.example.ewdj_ep3.advice;

import com.example.ewdj_ep3.domain.user.UserController;
import com.example.ewdj_ep3.exceptions.MatchNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice()
public class GlobalExceptionAdvice {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch() {
        return "error/404";
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public String handleStudentNotFound(MatchNotFoundException ex) {
        return "redirect:/matches/list";
    }
}
