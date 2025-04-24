package com.accenture.user_microservice.dtos;

import com.accenture.user_microservice.models.enums.RoleType;

public class UserDtoRole {

    private RoleType roleType;

    public UserDtoRole() {
    }

    public UserDtoRole(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
