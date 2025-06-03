package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-users")
    public ApiResponse<List<UserDtoOutput>> getAllUsers(HttpServletRequest httpServletRequest) {
        List<UserDtoOutput> userDtoOutputList = userService.getAll(httpServletRequest);

        return new ApiResponse<>(
                "List of all users",
                userDtoOutputList
        );
    }

    @PatchMapping("/change-role/{userId}")
    public ApiResponse<UserDtoEmailRole> changeRoleType(HttpServletRequest httpServletRequest,  @PathVariable Long userId,
                                                        @Valid @RequestBody UserDtoRole userDtoRole) {
        UserDtoEmailRole userDtoEmailRole = userService.changeRoleType(httpServletRequest, userId, userDtoRole);

        return new ApiResponse<>(
                "Role updated successfully",
                userDtoEmailRole
        );
    }
}
