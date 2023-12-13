package com.example.exceptions;

import lombok.Getter;

@Getter
public class AccessDeniedException extends Exception {
    private final String message;

    public AccessDeniedException(String message) {
        super(message);
        this.message = message;
    }
}
