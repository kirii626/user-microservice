package com.accenture.user_microservice.services.validations;

import com.accenture.user_microservice.config.security.JwtUtils;
import com.accenture.user_microservice.exceptions.ForbiddenAccessException;
import com.accenture.user_microservice.exceptions.InvalidAuthorizationHeaderException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidRoleTypeTest {

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private ValidRoleType validRoleType;

    @Test
    void validateAdminRole_shouldPass_whenRoleIsAdmin() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn("ADMIN");

        assertDoesNotThrow(() -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowInvalidAuthorizationHeaderException_whenHeaderIsMissing() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        assertThrows(InvalidAuthorizationHeaderException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowInvalidAuthorizationHeaderException_whenHeaderIsMalformed() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("BadToken abc");

        assertThrows(InvalidAuthorizationHeaderException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowForbiddenAccessException_whenRoleIsNotAdmin() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn("USER");

        assertThrows(ForbiddenAccessException.class,
                () -> validRoleType.validateAdminRole(request));
    }

    @Test
    void validateAdminRole_shouldThrowForbiddenAccessException_whenRoleIsNull() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String token = "mocked-token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtils.extractRole(token)).thenReturn(null);

        assertThrows(ForbiddenAccessException.class,
                () -> validRoleType.validateAdminRole(request));
    }
}
