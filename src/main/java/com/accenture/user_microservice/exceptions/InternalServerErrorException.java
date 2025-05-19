package com.accenture.user_microservice.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ApiException {
    public InternalServerErrorException(String message, Throwable throwable) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, throwable);
    }
}
