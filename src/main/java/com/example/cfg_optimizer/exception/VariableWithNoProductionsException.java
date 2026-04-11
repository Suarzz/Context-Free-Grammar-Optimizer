package com.example.cfg_optimizer.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VariableWithNoProductionsException extends RuntimeException {
    public VariableWithNoProductionsException(String message) {
        super(message);
    }
}
