package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.config.security.JwtUtils;
import com.accenture.user_microservice.dtos.UserDtoInput;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.services.AuthService;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthServiceImpl(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    public ApiResponse<UserDtoOutput> createUser(UserDtoInput userDtoInput) {
        return userService.createUser(userDtoInput);
    }

    @Override
    public String authenticateAndGenerateToken(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String username = authentication.getName();

            String roleType = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority())
                    .orElse("USER");

            Map<String, String> claims = new HashMap<>();
            claims.put("roleType", roleType);

            return jwtUtils.generateToken(username, claims);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
