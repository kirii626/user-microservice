package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.config.security.JwtUtils;
import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.exceptions.InternalServerErrorException;
import com.accenture.user_microservice.exceptions.InvalidCredentialsException;
import com.accenture.user_microservice.services.AuthService;
import com.accenture.user_microservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public UserDtoOutput createUser(UserDtoInput userDtoInput) {
        return userService.createUser(userDtoInput);
    }

    @Override
    public String authenticateAndGenerateToken(String email, String password) {
        log.info("Starting authentication for email {}", email);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String username = authentication.getName();

            String roleType = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("USER");

            Map<String, String> claims = new HashMap<>();
            claims.put("roleType", roleType);

            String token = jwtUtils.generateToken(username, claims);

            log.info("User '{}' authenticated successfully with role '{}'", username, roleType);

            return token;
        } catch (BadCredentialsException ex) {
            log.warn("Authentication failed for email '{}': {}", email, ex.getMessage());
            throw new InvalidCredentialsException();
        } catch (Exception ex) {
            log.error("Unexpected error during authentication for email '{}'", email, ex);
            throw new InternalServerErrorException("Unexpected error during authentication", ex);
        }
    }
}
