package com.accenture.user_microservice.services.mappers;

import com.accenture.user_microservice.dtos.input.UserDtoInput;
import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.models.UserEntity;
import com.accenture.user_microservice.models.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper(passwordEncoder);
    }

    @Test
    void userDtoInputToEntity_shouldMapCorrectly() {
        UserDtoInput input = new UserDtoInput("testuser", "test@mail.com", "rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        UserEntity result = userMapper.userDtoInputToEntity(input);

        assertEquals("testuser", result.getUsername());
        assertEquals("test@mail.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());

        verify(passwordEncoder).encode("rawPassword");
    }

    @Test
    void toUserDtoOutput_shouldMapCorrectly() throws NoSuchFieldException, IllegalAccessException {
        UserEntity entity = new UserEntity();
        entity.setUsername("testuser");
        entity.setEmail("test@mail.com");

        Field idField = UserEntity.class.getDeclaredField("userId");
        idField.setAccessible(true);
        idField.set(entity, 42L);

        UserDtoOutput result = userMapper.toUserDtoOutput(entity);

        assertEquals(42L, result.getUserId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@mail.com", result.getEmail());
    }

    @Test
    void toUserDtoOutputList_shouldMapListCorrectly() {
        UserEntity entity1 = new UserEntity("user1", "email1@mail.com", null, null);
        UserEntity entity2 = new UserEntity("user2", "email2@mail.com", null, null);

        List<UserDtoOutput> result = userMapper.toUserDtoOutputList(List.of(entity1, entity2));

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    void toUserDtoEmailRole_shouldMapCorrectly() {
        UserEntity entity = new UserEntity();
        entity.setEmail("admin@mail.com");
        entity.setRoleType(RoleType.ADMIN);

        UserDtoEmailRole result = userMapper.toUserDtoEmailRole(entity);

        assertEquals("admin@mail.com", result.getEmail());
        assertEquals(RoleType.ADMIN, result.getRoleType());
    }

    @Test
    void toUserDtoIdUsernameEmail_shouldMapCorrectly() throws NoSuchFieldException, IllegalAccessException {
        UserEntity entity = new UserEntity();
        entity.setUsername("john");
        entity.setEmail("john@mail.com");

        Field idField = UserEntity.class.getDeclaredField("userId");
        idField.setAccessible(true);
        idField.set(entity, 42L);

        UserDtoIdUsernameEmail result = userMapper.toUserDtoIdUsernameEmail(entity);

        assertEquals(42L, result.getUserId());
        assertEquals("john", result.getUsername());
        assertEquals("john@mail.com", result.getEmail());
    }
}
