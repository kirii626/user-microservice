package com.accenture.user_microservice.services.implementations;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import com.accenture.user_microservice.exceptions.ForbiddenAccessException;
import com.accenture.user_microservice.exceptions.InternalServerErrorException;
import com.accenture.user_microservice.exceptions.UserNotFoundException;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.models.enums.RoleType;
import com.accenture.user_microservice.repositories.UserRepository;
import com.accenture.user_microservice.services.mappers.UserMapper;
import com.accenture.user_microservice.services.validations.ValidRoleType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ValidRoleType validRoleType;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private HttpServletRequest request;

    @Test
    void getAll_shouldReturnListOfUsers() {
        List<UserEntity> userEntities = List.of(new UserEntity(), new UserEntity());
        List<UserDtoOutput> userDtos = List.of(
                new UserDtoOutput(1L, "user1", "user1@mail.com"),
                new UserDtoOutput(2L, "user2", "user2@mail.com")
        );

        when(userRepository.findAll()).thenReturn(userEntities);
        when(userMapper.toUserDtoOutputList(userEntities)).thenReturn(userDtos);

        List<UserDtoOutput> result = userService.getAll(request);

        verify(validRoleType).validateAdminRole(request);
        assertEquals(2, result.size());
        assertEquals("user1@mail.com", result.get(0).getEmail());
    }

    @Test
    void getAll_shouldThrowForbiddenAccessException_whenRoleIncorrect() {
        doThrow(new ForbiddenAccessException()).when(validRoleType).validateAdminRole(request);

        assertThrows(ForbiddenAccessException.class, () -> {
            userService.getAll(request);
        });

        verify(validRoleType).validateAdminRole(request);
    }

    @Test
    void getAll_shouldThrowInternalServerErrorException_whenUnexpectedErrorOccurs() {

        doThrow(new RuntimeException("Unexpected")).when(validRoleType).validateAdminRole(request);

        assertThrows(InternalServerErrorException.class, () -> {
            userService.getAll(request);
        });

        verify(validRoleType).validateAdminRole(request);
    }

    @Test
    void changeRoleType_shouldUpdateAndReturnUser() {
        Long userId = 1L;
        UserDtoRole dtoRole = new UserDtoRole(RoleType.ADMIN);
        UserEntity user = new UserEntity();
        user.setRoleType(RoleType.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        UserDtoEmailRole expectedDto = new UserDtoEmailRole("user@example.com", RoleType.ADMIN);
        when(userMapper.toUserDtoEmailRole(user)).thenReturn(expectedDto);

        UserDtoEmailRole result = userService.changeRoleType(request, userId, dtoRole);

        verify(validRoleType).validateAdminRole(request);
        assertEquals(RoleType.ADMIN, result.getRoleType());
    }

    @Test
    void changeRoleType_shouldThrowInternalServerErrorException_whenUnexpectedErrorOccurs() {
        Long userId = 1L;
        UserDtoRole role = new UserDtoRole(RoleType.ADMIN);

        when(userRepository.findById(userId))
                .thenThrow(new RuntimeException("Database failure"));

        assertThrows(InternalServerErrorException.class, () -> {
            userService.changeRoleType(request, userId, role);
        });

        verify(userRepository).findById(userId);
    }

    @Test
    void changeRoleType_shouldThrowForbiddenAccess_whenRoleIncorrect() {
        Long userId = 1L;
        UserDtoRole role = new UserDtoRole(RoleType.USER);

        when(userRepository.findById(userId))
                .thenThrow(new ForbiddenAccessException());

        assertThrows(ForbiddenAccessException.class, () -> {
            userService.changeRoleType(request, userId, role);
        });

        verify(userRepository).findById(userId);
    }

    @Test
    void createUser_shouldSaveAndReturnUser() {
        UserDtoInput input = new UserDtoInput("newuser", "email@mail.com", "pass123");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("email@mail.com");

        when(userMapper.userDtoInputToEntity(input)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        UserDtoOutput output = new UserDtoOutput(1L, "newuser", "email@mail.com");
        when(userMapper.toUserDtoOutput(userEntity)).thenReturn(output);

        UserDtoOutput result = userService.createUser(input);

        assertEquals("email@mail.com", result.getEmail());
    }

    @Test
    void createUser_shouldThrowInternalServerErrorException_whenUnexpectedErrorOccurs() {
        UserDtoInput input = new UserDtoInput("user", "email@mail.com", "pass");

        when(userMapper.userDtoInputToEntity(input))
                .thenThrow(new RuntimeException("Mapping failure"));

        assertThrows(InternalServerErrorException.class, () -> {
            userService.createUser(input);
        });

        verify(userMapper).userDtoInputToEntity(input);
    }


    @Test
    void getUserById_shouldReturnUserIfExists() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("found@mail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        UserDtoIdUsernameEmail expectedDto = new UserDtoIdUsernameEmail(userId, "found", "found@mail.com");
        when(userMapper.toUserDtoIdUsernameEmail(userEntity)).thenReturn(expectedDto);

        UserDtoIdUsernameEmail result = userService.getUserById(userId);

        assertEquals("found@mail.com", result.getEmail());
    }


    @Test
    void getUserById_shouldThrowIfUserNotFound() {
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }


}
