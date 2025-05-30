package com.accenture.user_microservice.services;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    List<UserDtoOutput> getAll(HttpServletRequest httpServletRequest);

    UserDtoEmailRole changeRoleType(HttpServletRequest httpServletRequest, Long userId, UserDtoRole userDtoRole);

    UserDtoOutput createUser(UserDtoInput userDtoInput);

    UserDtoIdUsernameEmail getUserById(Long userId);
}
