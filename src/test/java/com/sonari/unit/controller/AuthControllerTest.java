package com.sonari.unit.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sonari.controller.AuthController;
import com.sonari.dto.AuthRequestDTO;
import com.sonari.dto.TokenResponseDTO;
import com.sonari.repository.UserRepository;
import com.sonari.service.AuthService;
import com.sonari.service.TokenService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
        
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void shouldAuthUser() throws Exception{
        AuthRequestDTO authRequest = new AuthRequestDTO(
            "test",
            "test-password"
        );

        TokenResponseDTO tokenResponse = new TokenResponseDTO(
            "this-is-a-token"
        );

        when(authService.auth(authRequest))
            .thenReturn(tokenResponse);

        mockMvc.perform(
            post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenResponse))
        )
        .andExpect(status().isOk());
    }
}
