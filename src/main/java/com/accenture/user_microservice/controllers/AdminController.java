package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-users")
    public ApiResponse<ArrayList<UserDtoOutput>> getAllUsers() {
        ArrayList<UserDtoOutput> userDtoOutputList = userService.getAll();

        return new ApiResponse<>(
                "List of all users",
                userDtoOutputList
        );
    }

    @PatchMapping("/change-role/{userId}")
    public ApiResponse<UserDtoEmailRole> changeRoleType(@PathVariable Long userId,
                                                        @Valid @RequestBody UserDtoRole userDtoRole) {
        UserDtoEmailRole userDtoEmailRole = userService.changeRoleType(userId, userDtoRole);

        return new ApiResponse<>(
                "Role updated successfully",
                userDtoEmailRole
        );
    }
}
