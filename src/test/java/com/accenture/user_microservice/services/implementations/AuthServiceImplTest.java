package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.config.security.JwtUtils;
import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.exceptions.InvalidCredentialsException;
import com.accenture.user_microservice.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void createUser_shouldDelegateToUserService() {
        UserDtoInput input = new UserDtoInput("user", "user@mail.com", "pass123");
        UserDtoOutput expectedOutput = new UserDtoOutput(1L, "user", "user@mail.com");

        when(userService.createUser(input)).thenReturn(expectedOutput);

        UserDtoOutput result = authService.createUser(input);

        assertEquals(expectedOutput.getEmail(), result.getEmail());
        verify(userService).createUser(input);
    }

    @Test
    void authenticateAndGenerateToken_shouldThrowInvalidCredentialsException_onBadCredentials() {
        String email = "baduser@mail.com";
        String password = "wrongpass";

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(InvalidCredentialsException.class, () -> {
            authService.authenticateAndGenerateToken(email, password);
        });
    }

}
