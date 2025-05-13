package com.accenture.user_microservice.services.mappers;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity userDtoInputToEntity(UserDtoInput userDtoInput) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDtoInput.getUsername());
        userEntity.setEmail(userDtoInput.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDtoInput.getPassword()));

        return userEntity;
    }

    public UserDtoOutput toUserDtoOutput(UserEntity userEntity) {
        return new UserDtoOutput(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }

    public List<UserDtoOutput> toUserDtoOutputList(List<UserEntity> userEntityList) {
        return userEntityList
                .stream()
                .map(this::toUserDtoOutput)
                .collect(Collectors.toList());
    }

    public UserDtoEmailRole toUserDtoEmailRole(UserEntity userEntity) {
        return new UserDtoEmailRole(
                userEntity.getEmail(),
                userEntity.getRoleType()
        );
    }

    public UserDtoIdUsernameEmail toUserDtoIdUsernameEmail(UserEntity userEntity) {
        return new UserDtoIdUsernameEmail(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }
}
