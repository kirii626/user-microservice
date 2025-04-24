package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.dtos.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.UserDtoInput;
import com.accenture.user_microservice.dtos.UserDtoOutput;
import com.accenture.user_microservice.dtos.UserDtoRole;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.repositories.UserRepository;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.services.mappers.UserMapper;
import com.accenture.user_microservice.utils.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<List<UserDtoOutput>> getAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDtoOutput> userDtoOutputList = userMapper.toUserDtoOutputList(userEntityList);

        ApiResponse response = new ApiResponse<>(
                "All users:",
                userDtoOutputList
        );

        return response;
    }

    @Override
    public ApiResponse<UserDtoEmailRole> changeRoleType(Long userId, UserDtoRole userDtoRole) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found by ID:"));
        userEntity.setRoleType(userDtoRole.getRoleType());
        UserEntity savedEntity = userRepository.save(userEntity);

        UserDtoEmailRole userDtoEmailRole = userMapper.toUserDtoEmailRole(savedEntity);

        ApiResponse response = new ApiResponse<>(
                "Role updated successfully",
                userDtoEmailRole
        );
        return response;
    }

    @Override
    public ApiResponse<UserDtoOutput> createUser(UserDtoInput userDtoInput) {
        UserEntity userEntity = userMapper.userDtoInputToEntity(userDtoInput);
        userRepository.save(userEntity);

        UserDtoOutput userDtoOutput = userMapper.toUserDtoOutput(userEntity);

        ApiResponse response = new ApiResponse<>(
                "User created successfully",
                userDtoOutput
        );

        return response;
    }


}
