package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.UserDtoInput;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ApiResponse<UserDtoOutput> createUser(@Valid @RequestBody UserDtoInput userDtoInput) {
        return userService.createUser(userDtoInput);
    }
}