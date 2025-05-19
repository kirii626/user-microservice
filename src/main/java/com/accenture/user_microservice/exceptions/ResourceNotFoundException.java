package com.accenture.user_microservice.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
  public ResourceNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
  }

  public ResourceNotFoundException(String message, Throwable throwable) {
    super(message, HttpStatus.NOT_FOUND, throwable);
  }
}
