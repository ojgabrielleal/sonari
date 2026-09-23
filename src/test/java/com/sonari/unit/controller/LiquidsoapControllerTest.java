package com.sonari.unit.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sonari.controller.LiquidsoapController;
import com.sonari.infrastructure.Liquidsoap;
import com.sonari.repository.UserRepository;
import com.sonari.service.TokenService;

@WebMvcTest(LiquidsoapController.class)
public class LiquidsoapControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private Liquidsoap liquidsoap;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test 
    void shouldGenerateBuildLiquidsoap() throws Exception{
        mockMvc.perform(
            post("/liquidsoap")
        )
        .andExpect(status().isOk());

        verify(liquidsoap).generateConfiguration();
    }
}
