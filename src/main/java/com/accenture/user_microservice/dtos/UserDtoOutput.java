package com.accenture.user_microservice.dtos;

public class UserDtoOutput {

    private Long userId;

    private String username;

    private String email;

    public UserDtoOutput() {
    }

    public UserDtoOutput(Long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
