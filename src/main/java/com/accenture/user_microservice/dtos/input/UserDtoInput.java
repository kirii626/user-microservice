package com.accenture.user_microservice.dtos.input;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoInput {

    @NotBlank(message = "The username can't be null")
    private String username;

    @NotBlank(message = "The email can't be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "The password can't be null")
    @Size(min = 4, message = "The password must be at least 4 characters")
    private String password;

}
