package com.example.exceptions;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {
    private final String message;

    public RestApiException(String message) {
        super(message);
        this.message = message;
    }
}
