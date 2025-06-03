package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import com.accenture.user_microservice.exceptions.InternalServerErrorException;
import com.accenture.user_microservice.exceptions.UserNotFoundException;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.models.enums.RoleType;
import com.accenture.user_microservice.repositories.UserRepository;
import com.accenture.user_microservice.services.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getAll_shouldReturnListOfUsers() {
        List<UserEntity> entities = List.of(new UserEntity(), new UserEntity());
        List<UserDtoOutput> dtoList = List.of(new UserDtoOutput(), new UserDtoOutput());

        when(userRepository.findAll()).thenReturn(entities);
        when(userMapper.toUserDtoOutputList(entities)).thenReturn(dtoList);

        ArrayList<UserDtoOutput> result = userService.getAll();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
        verify(userMapper).toUserDtoOutputList(entities);
    }

    @Test
    void getAll_shouldThrowInternalServerErrorException_onException() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("DB error"));

        assertThrows(InternalServerErrorException.class, () -> userService.getAll());
    }

    @Test
    void changeRoleType_shouldUpdateUserRole() {
        Long userId = 1L;
        UserDtoRole dtoRole = new UserDtoRole(RoleType.ADMIN);
        UserEntity entity = new UserEntity();
        setField(entity, "userId", 1L);
        UserEntity savedEntity = new UserEntity();
        savedEntity.setRoleType(RoleType.ADMIN);

        UserDtoEmailRole expectedDto = new UserDtoEmailRole("user@example.com", RoleType.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        when(userRepository.save(entity)).thenReturn(savedEntity);
        when(userMapper.toUserDtoEmailRole(savedEntity)).thenReturn(expectedDto);

        UserDtoEmailRole result = userService.changeRoleType(userId, dtoRole);

        assertEquals(RoleType.ADMIN, result.getRoleType());
        verify(userRepository).save(entity);
    }

    @Test
    void changeRoleType_shouldThrowInternalServerErrorException_onError() {
        Long userId = 1L;
        UserDtoRole dtoRole = new UserDtoRole(RoleType.ADMIN);

        when(userRepository.findById(userId)).thenThrow(new RuntimeException("DB error"));

        assertThrows(InternalServerErrorException.class, () -> userService.changeRoleType(userId, dtoRole));
    }

    @Test
    void createUser_shouldSaveAndReturnDto() {
        UserDtoInput input = new UserDtoInput("newuser@example.com", "password", "USER");
        UserEntity entity = new UserEntity();
        setField(entity, "userId", 1L);
        UserDtoOutput dtoOutput = new UserDtoOutput(1L,"testuser", "newuser@example.com");

        when(userMapper.userDtoInputToEntity(input)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.toUserDtoOutput(entity)).thenReturn(dtoOutput);

        UserDtoOutput result = userService.createUser(input);

        assertEquals("newuser@example.com", result.getEmail());
        verify(userRepository).save(entity);
    }

    @Test
    void createUser_shouldThrowInternalServerErrorException_onError() {
        UserDtoInput input = new UserDtoInput("fail@example.com", "123", "USER");
        when(userMapper.userDtoInputToEntity(input)).thenThrow(new RuntimeException("Map error"));

        assertThrows(InternalServerErrorException.class, () -> userService.createUser(input));
    }

    @Test
    void getUserById_shouldReturnUserDto() {
        Long userId = 1L;
        UserEntity entity = new UserEntity();
        UserDtoIdUsernameEmail dto = new UserDtoIdUsernameEmail(userId, "user", "user@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(entity));
        when(userMapper.toUserDtoIdUsernameEmail(entity)).thenReturn(dto);

        UserDtoIdUsernameEmail result = userService.getUserById(userId);

        assertEquals(userId, result.getUserId());
        assertEquals("user@example.com", result.getEmail());
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException() {
        Long userId = 404L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUserById_shouldThrowInternalServerErrorException_onError() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenThrow(new RuntimeException("DB error"));

        assertThrows(InternalServerErrorException.class, () -> userService.getUserById(userId));
    }

}
