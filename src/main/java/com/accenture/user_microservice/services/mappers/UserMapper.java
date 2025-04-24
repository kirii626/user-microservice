package com.accenture.user_microservice.services.mappers;

import com.accenture.user_microservice.dtos.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.UserDtoInput;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.models.enums.RoleType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserEntity userDtoInputToEntity(UserDtoInput userDtoInput) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDtoInput.getUsername());
        userEntity.setEmail(userDtoInput.getEmail());
        userEntity.setPassword(userDtoInput.getPassword());

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

    public static UserDtoEmailRole toUserDtoEmailRole(UserEntity userEntity) {
        return new UserDtoEmailRole(
                userEntity.getEmail(),
                userEntity.getRoleType()
        );
    }
}
