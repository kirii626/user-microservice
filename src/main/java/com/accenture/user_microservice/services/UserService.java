package com.accenture.user_microservice.services;

import com.accenture.user_microservice.dtos.*;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    ApiResponse<List<UserDtoOutput>> getAll();

    ApiResponse<UserDtoEmailRole> changeRoleType(@Valid Long userId, UserDtoRole userDtoRole);

    ApiResponse<UserDtoOutput> createUser(@Valid UserDtoInput userDtoInput);

    UserDtoIdUsernameEmail getUserById(Long userId);
}
