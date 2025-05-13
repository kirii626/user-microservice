package com.accenture.user_microservice.dtos.input;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoInput {

    private String username;

    private String email;

    private String password;

}
