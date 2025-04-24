package com.accenture.user_microservice.dtos;

public class UserDtoInput {

    private String username;

    private String email;

    private String password;

    public UserDtoInput() {
    }

    public UserDtoInput(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
