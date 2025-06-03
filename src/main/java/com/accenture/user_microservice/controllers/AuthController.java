package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.input.UserDtoEmailPassword;
import com.accenture.user_microservice.services.AuthService;
import com.accenture.user_microservice.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserDtoOutput> registerUser(@Valid @RequestBody UserDtoInput userDtoInput) {
        UserDtoOutput userDtoOutput = authService.createUser(userDtoInput);

        return new ApiResponse<>(
                "User created successfully",
                userDtoOutput
        );
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserDtoEmailPassword userDtoEmailPassword) {
        String jwt = authService.authenticateAndGenerateToken(
                userDtoEmailPassword.getEmail(),
                userDtoEmailPassword.getPassword());
        return ResponseEntity.ok(jwt);
    }
}
