package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.dto.CreateUserDTO;
import com.lunifer.jo.fpshoppingcart.dto.PagedResponse;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetAllUsers() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setActive(true);

        PagedResponse<UserDTO> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(Collections.singletonList(userDTO));
        pagedResponse.setPageNumber(0);
        pagedResponse.setPageSize(10);
        pagedResponse.setTotalElements(1L);
        pagedResponse.setTotalPages(1);

        when(userService.getAllUsers(any(PageRequest.class))).thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/api/users")
                .param("page", "0")
                .param("size", "10")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Users retrieved successfully"))
                .andExpect(jsonPath("$.data.content[0].username").value("testuser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateUser() throws Exception {
        // Given
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("newuser");
        createUserDTO.setEmail("newuser@example.com");
        createUserDTO.setFirstName("New");
        createUserDTO.setLastName("User");
        createUserDTO.setPassword("password123");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("newuser");
        userDTO.setEmail("newuser@example.com");
        userDTO.setFirstName("New");
        userDTO.setLastName("User");
        userDTO.setActive(true);

        when(userService.createUser(any(CreateUserDTO.class))).thenReturn(userDTO);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.data.username").value("newuser"));
    }

    @Test
    @WithMockUser
    void shouldGetUserById() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");
        userDTO.setActive(true);

        when(userService.getUserById(1L)).thenReturn(userDTO);

        // When & Then
        mockMvc.perform(get("/api/users/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User found"))
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }
}
