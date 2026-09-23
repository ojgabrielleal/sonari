package com.sonari.unit.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sonari.controller.MediaController;
import com.sonari.dto.MediaResponseDTO;
import com.sonari.repository.UserRepository;
import com.sonari.service.MediaService;
import com.sonari.service.TokenService;

@WebMvcTest(MediaController.class)
public class MediaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
 
    @MockitoBean
    private MediaService mediaService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void shouldListAllFiles() throws Exception{
        MediaResponseDTO mediaResponse = new MediaResponseDTO(
            "test",
            "file",
            "/temp/media.mp3",
            List.of()
        );

        when(mediaService.index())
            .thenReturn(mediaResponse);

        mockMvc.perform(get("/medias"))
            .andExpect(status().isOk());
    }
}
