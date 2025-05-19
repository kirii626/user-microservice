package com.accenture.user_microservice.dtos.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoEmailPassword {

    @NotBlank(message = "The email can't be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "The password can't be null")
    private String password;
}
