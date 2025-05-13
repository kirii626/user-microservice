package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.dtos.UserDtoRole;
import com.accenture.user_microservice.dtos.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-users")
    public ApiResponse<List<UserDtoOutput>> getAllUsers() {
        List<UserDtoOutput> userDtoOutputList = userService.getAll();

        ApiResponse<List<UserDtoOutput>> response = new ApiResponse<>(
                "List of all users",
                userDtoOutputList
        );

        return response;
    }

    @PatchMapping("/change-role/{userId}")
    public ApiResponse<UserDtoEmailRole> changeRoleType(@Valid @PathVariable Long userId,
                                                        @RequestBody UserDtoRole userDtoRole) {
        UserDtoEmailRole userDtoEmailRole = userService.changeRoleType(userId, userDtoRole);

        ApiResponse<UserDtoEmailRole> response = new ApiResponse<>(
                "Role updated successfully",
                userDtoEmailRole
        );

        return response;
    }

    @GetMapping("/user-by-id/{userId}")
    public UserDtoIdUsernameEmail getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }
}
