package com.accenture.user_microservice.dtos;

import com.accenture.user_microservice.models.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoEmailRole {

    private String email;

    private RoleType roleType;

}
