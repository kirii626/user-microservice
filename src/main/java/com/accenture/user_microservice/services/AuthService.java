package com.accenture.user_microservice.services;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;

public interface AuthService {

    UserDtoOutput createUser(UserDtoInput userDtoInput);

    String authenticateAndGenerateToken(String email, String password);
}
