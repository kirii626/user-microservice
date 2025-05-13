package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.dtos.*;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.repositories.UserRepository;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.services.mappers.UserMapper;
import com.accenture.user_microservice.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Cacheable("users")
    public List<UserDtoOutput> getAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDtoOutput> userDtoOutputList = userMapper.toUserDtoOutputList(userEntityList);

        return userDtoOutputList;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public UserDtoEmailRole changeRoleType(Long userId, UserDtoRole userDtoRole) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found by ID:"));
        userEntity.setRoleType(userDtoRole.getRoleType());
        UserEntity savedEntity = userRepository.save(userEntity);

        UserDtoEmailRole userDtoEmailRole = userMapper.toUserDtoEmailRole(savedEntity);

        return userDtoEmailRole;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public UserDtoOutput createUser(UserDtoInput userDtoInput) {
        UserEntity userEntity = userMapper.userDtoInputToEntity(userDtoInput);
        userRepository.save(userEntity);

        UserDtoOutput userDtoOutput = userMapper.toUserDtoOutput(userEntity);

        return userDtoOutput;
    }

    @Override
    @Cacheable(value = "userById", key = "#userId")
    public UserDtoIdUsernameEmail getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found by ID"));

        UserDtoIdUsernameEmail userDtoIdUsernameEmail = userMapper.toUserDtoIdUsernameEmail(userEntity);
        return userDtoIdUsernameEmail;
    }


}
