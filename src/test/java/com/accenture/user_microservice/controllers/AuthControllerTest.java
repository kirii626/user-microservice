package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.input.UserDtoEmailPassword;
import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.services.AuthService;
import com.accenture.user_microservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void registerUser_shouldReturnUserRegistered() throws Exception {
        UserDtoInput input = new UserDtoInput("newuser", "new@example.com", "password123");
        UserDtoOutput output = new UserDtoOutput(1L, "newuser", "new@example.com");

        when(authService.createUser(any(UserDtoInput.class))).thenReturn(output);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.username").value("newuser"))
                .andExpect(jsonPath("$.data.email").value("new@example.com"));

    }

    @Test
    void login_shouldReturnJwtToken() throws Exception {
        UserDtoEmailPassword loginRequest = new UserDtoEmailPassword("user@example.com", "password123");
        String expectedJwt = "mocked-jwt-token";

        when(authService.authenticateAndGenerateToken(anyString(), anyString()))
                .thenReturn(expectedJwt);

        mockMvc.perform(post("/api/auth/log-in") // cambia a tu path real
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJwt));
    }
}
