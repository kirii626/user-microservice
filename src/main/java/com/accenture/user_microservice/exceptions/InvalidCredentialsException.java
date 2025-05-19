package com.accenture.user_microservice.exceptions;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }

    public InvalidCredentialsException(Throwable cause) {
        super("Invalid email or password", cause);
    }
}
