package com.accenture.user_microservice.dtos;

import com.accenture.user_microservice.models.enums.RoleType;

public class UserDtoEmailRole {

    private String email;

    private RoleType roleType;

    public UserDtoEmailRole() {
    }

    public UserDtoEmailRole(String email, RoleType roleType) {
        this.email = email;
        this.roleType = roleType;
    }

    public String getEmail() {
        return email;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
