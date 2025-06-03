package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import com.accenture.user_microservice.exceptions.InternalServerErrorException;
import com.accenture.user_microservice.exceptions.UserNotFoundException;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.repositories.UserRepository;
import com.accenture.user_microservice.services.UserService;
import com.accenture.user_microservice.services.mappers.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Cacheable(value = "users", key = "'allUsers'")
    public ArrayList<UserDtoOutput> getAll() {
        log.info("Fetching all users");

        try {
            log.debug("Admin role validated for getAll");
            List<UserEntity> userEntityList = userRepository.findAll();

            List<UserDtoOutput> userDtoOutputList = userMapper.toUserDtoOutputList(userEntityList);
            ArrayList<UserDtoOutput> userDtoOutputArrayList = new ArrayList<>(userDtoOutputList);

            log.info("Fetched {} users", userEntityList.size());
            return userDtoOutputArrayList;
        } catch (Exception ex) {
            log.error("Unexpected error during fetching users", ex);
            throw new InternalServerErrorException("Unexpected error during fetching users", ex);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserDtoEmailRole changeRoleType(Long userId, UserDtoRole userDtoRole) {
        log.info("Starting processes for update role type of user {}", userId);

        try {
            log.debug("Admin role validated for changeRoleType");

            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            userEntity.setRoleType(userDtoRole.getRoleType());
            UserEntity savedEntity = userRepository.save(userEntity);

            UserDtoEmailRole userDtoEmailRole = userMapper.toUserDtoEmailRole(savedEntity);

            log.info("Updated role for user {} to {}", userId, userDtoEmailRole.getRoleType());

            return userDtoEmailRole;
        } catch (Exception ex) {
            log.error("Unexpected error while updating user", ex);
            throw new InternalServerErrorException("Unexpected error while updating user's role", ex);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserDtoOutput createUser(UserDtoInput userDtoInput) {
        log.info("Starting processes to create a new user");

        try {
            UserEntity userEntity = userMapper.userDtoInputToEntity(userDtoInput);
            userRepository.save(userEntity);

            UserDtoOutput userDtoOutput = userMapper.toUserDtoOutput(userEntity);

            log.info("User created: {}", userDtoOutput.getEmail());

            return userDtoOutput;
        } catch (Exception ex) {
            log.error("Unexpected error while creating user", ex);
            throw new InternalServerErrorException("Unexpected error while creating user", ex);
        }
    }

    @Override
    @Cacheable(value = "userById", key = "#userId")
    public UserDtoIdUsernameEmail getUserById(Long userId) {
        log.info("Starting search of user by ID {}", userId);

        try {
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));

            UserDtoIdUsernameEmail userDtoIdUsernameEmail = userMapper.toUserDtoIdUsernameEmail(userEntity);

            log.info("User found by ID: {} | Email: {}", userDtoIdUsernameEmail.getUserId(), userDtoIdUsernameEmail.getEmail());

            return userDtoIdUsernameEmail;
        } catch (UserNotFoundException ex) {
            log.warn("User not found by ID: {}", userId);
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while searching user by its ID", ex);
            throw new InternalServerErrorException("Unexpected error while searching user by its ID", ex);
        }
    }
}
