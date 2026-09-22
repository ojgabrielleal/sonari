package com.sonari.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sonari.controller.UserController;
import com.sonari.dto.UserRequestDTO;
import com.sonari.dto.UserResponseDTO;
import com.sonari.entity.User;
import com.sonari.factories.UserFactory;
import com.sonari.repository.UserRepository;
import com.sonari.service.TokenService;
import com.sonari.service.UserService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void shouldListUsers() throws Exception{
        List<UserResponseDTO> users = UserFactory.createWithResponse(10);

        when(userService.index())
            .thenReturn(users);

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldShowUniqueUser() throws Exception{
        UserResponseDTO userResponse = UserFactory.createWithResponse();

        when(userService.show(userResponse.uuid()))
            .thenReturn(userResponse);

        mockMvc.perform(get("/users/" + userResponse.uuid()))
            .andExpect(status().isOk());
    }

    @Test 
    void shouldStoreUser() throws Exception{
        UserRequestDTO userRequest = UserFactory.createWithRequest();
        UserResponseDTO userResponse = UserFactory.createWithResponse();

        when(userService.store(userRequest))
            .thenReturn(userResponse);

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
        .andExpect(status().isOk());
    }

    @Test 
    void shouldReturnBadRequestWhenFullNameIsBlankOnCreateUser() throws Exception{
        UserRequestDTO userRequest = new UserRequestDTO(
            "",
            "user_test"
        );

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))        
        )
        .andExpect(status().isBadRequest());

        verify(userService, never()).store(any());
    }

    @Test
    void shouldUpdateUser() throws Exception{
        UserRequestDTO userRequest = UserFactory.createWithRequest();
        UserResponseDTO userResponse = UserFactory.createWithResponse();

        when(userService.update(userResponse.uuid(), userRequest))
            .thenReturn(userResponse);

        mockMvc.perform(
            put("/users/{uuid}", userResponse.uuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))        
        )
        .andExpect(status().isOk());
    }

    @Test 
    void shouldReturnBadRequestWhenFullNameIsBlankOnUpdateUser() throws Exception{
        User user = UserFactory.create();

        UserRequestDTO userRequest = new UserRequestDTO(
            "",
            "user_test"
        );

        mockMvc.perform(
            put("/users/{uuid}", user.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))        
        )
        .andExpect(status().isBadRequest());

        verify(userService, never()).update(any(), any());
    }

    @Test 
    void shouldDeleteUser() throws Exception{
        User user = UserFactory.create();
        
        mockMvc.perform(delete("/users/{uuid}", user.getUuid()))
            .andExpect(status().isOk());

        verify(userService).delete(user.getUuid());
    }
}
