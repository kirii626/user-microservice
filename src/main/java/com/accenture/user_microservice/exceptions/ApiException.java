package com.accenture.user_microservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ApiException extends RuntimeException {
    private final HttpStatus status;

    protected ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    protected ApiException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
