package com.accenture.user_microservice.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoOutput {

    private Long userId;

    private String username;

    private String email;

}
