package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.dtos.UserDtoRole;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class UserAdminController {

    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ApiResponse<List<UserDtoOutput>> getAllUsers() {
        return userService.getAll();
    }

    @PatchMapping("/change-role/{userId}")
    public ApiResponse<UserDtoEmailRole> changeRoleType(@Valid @PathVariable Long userId,
                                                        @RequestBody UserDtoRole userDtoRole) {
        return userService.changeRoleType(userId, userDtoRole);
    }
}
