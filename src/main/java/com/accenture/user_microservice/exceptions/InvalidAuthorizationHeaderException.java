package com.accenture.user_microservice.exceptions;

public class InvalidAuthorizationHeaderException extends UnauthorizedException {
    public InvalidAuthorizationHeaderException() {
        super("Missing or invalid Authorization header");
    }
}
