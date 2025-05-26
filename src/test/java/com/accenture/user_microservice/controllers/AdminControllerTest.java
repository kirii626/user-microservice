package com.accenture.user_microservice.controllers;

import com.accenture.user_microservice.dtos.output.UserDtoEmailRole;
import com.accenture.user_microservice.dtos.output.UserDtoIdUsernameEmail;
import com.accenture.user_microservice.dtos.output.UserDtoOutput;
import com.accenture.user_microservice.dtos.output.UserDtoRole;
import com.accenture.user_microservice.models.enums.RoleType;
import com.accenture.user_microservice.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getAllUsers_shouldReturnListOfUsers() throws Exception {
        List<UserDtoOutput> mockUsers = List.of(
                new UserDtoOutput(1L, "user1", "user1@example.com"),
                new UserDtoOutput(2L, "user2", "user2@example.com")
        );

        when(userService.getAll(any(HttpServletRequest.class))).thenReturn(mockUsers);

        mockMvc.perform(get("/api/admin/all-users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("List of all users"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].username").value("user1"));
    }

    @Test
    void changeRole_shouldReturnUpdatedUser() throws Exception {
        UserDtoEmailRole updatedUser = new UserDtoEmailRole("user@example.com", RoleType.ADMIN);

        when(userService.changeRoleType(any(), eq(1L), any(UserDtoRole.class))).thenReturn(updatedUser);

        mockMvc.perform(patch("/api/admin/change-role/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "role": "ADMIN"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Role updated successfully"))
                .andExpect(jsonPath("$.data.email").value("user@example.com"));
    }

    @Test
    void getUserById_shouldReturnUserData() throws Exception {
        UserDtoIdUsernameEmail userDto = new UserDtoIdUsernameEmail(1L, "testuser", "test@example.com");

        when(userService.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/admin/user-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}
