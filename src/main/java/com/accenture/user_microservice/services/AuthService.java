package com.accenture.user_microservice.services;

import com.accenture.user_microservice.dtos.UserDtoInput;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ApiResponse<UserDtoOutput> createUser(UserDtoInput userDtoInput);

    String authenticateAndGenerateToken(String email, String password);
}
