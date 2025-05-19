package com.accenture.user_microservice.exceptions;


public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long userId) {
        super("User with ID "+userId+" not found");
    }

    public UserNotFoundException(Long userId, Throwable throwable) {
        super("User with ID "+userId+" not found", throwable);
    }
}
