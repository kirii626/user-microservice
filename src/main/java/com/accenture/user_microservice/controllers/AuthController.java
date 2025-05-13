package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.UserDtoInput;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.dtos.UserDtoEmailPassword;
import com.accenture.user_microservice.services.AuthService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ApiResponse<UserDtoOutput> registerUser(@Valid @RequestBody UserDtoInput userDtoInput) {
        UserDtoOutput userDtoOutput = authService.createUser(userDtoInput);

        ApiResponse<UserDtoOutput> response = new ApiResponse<>(
                "User created successfully",
                userDtoOutput
        );

        return response;
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserDtoEmailPassword userDtoEmailPassword) {
        String jwt = authService.authenticateAndGenerateToken(
                userDtoEmailPassword.getEmail(),
                userDtoEmailPassword.getPassword());
        return ResponseEntity.ok(jwt);
    }
}
