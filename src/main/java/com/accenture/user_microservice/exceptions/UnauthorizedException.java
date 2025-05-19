package com.accenture.user_microservice.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, Throwable throwable) {
        super(message, HttpStatus.UNAUTHORIZED, throwable);
    }
}
