package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/admin/internal-use")
public class InternalUseController {

    private final UserService userService;

    @GetMapping("/user-by-id/{userId}")
    public UserDtoIdUsernameEmail getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}
