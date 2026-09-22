package com.sonari.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sonari.controller.PlaylistController;
import com.sonari.dto.PlaylistMusicRequestDTO;
import com.sonari.dto.PlaylistMusicResponseDTO;
import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.entity.Playlist;
import com.sonari.entity.PlaylistMusic;
import com.sonari.factories.PlaylistFactory;
import com.sonari.factories.PlaylistMusicFactory;
import com.sonari.repository.UserRepository;
import com.sonari.service.PlaylistService;
import com.sonari.service.TokenService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlaylistService playlistService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void shouldReturnAllPlaylists() throws Exception{
        List<PlaylistResponseDTO> playlists = PlaylistFactory.createWithResponse(10);

        when(playlistService.index())
            .thenReturn(playlists);

        mockMvc.perform(get("/playlists"))
            .andExpect(status().isOk());
    }

    @Test 
    void shouldReturnUniquePlaylist() throws Exception{
        PlaylistResponseDTO playlistResponse = PlaylistFactory.createWithResponse();

        when(playlistService.show(playlistResponse.uuid()))
            .thenReturn(playlistResponse);

        mockMvc.perform(get("/playlists/{uuid}", playlistResponse.uuid()))
            .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePlaylist() throws Exception{
        PlaylistRequestDTO playlistRequest = PlaylistFactory.createWithRequest();
        PlaylistResponseDTO playlistResponse = PlaylistFactory.createWithResponse();

        when(playlistService.store(playlistRequest))
            .thenReturn(playlistResponse);

        mockMvc.perform(
            post("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playlistResponse))    
        )
        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenFullNameIsBlankOnCreatePlaylist() throws Exception{
        PlaylistRequestDTO playlistRequest = new PlaylistRequestDTO(
            "",
            5,
            List.of()
        );   
        
        mockMvc.perform(
            post("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playlistRequest))        
        )
        .andExpect(status().isBadRequest());

        verify(playlistService, never()).store(any());
    }

    @Test
    void shouldUpdatePlaylist() throws Exception{
        PlaylistRequestDTO playlistRequest = PlaylistFactory.createWithRequest(); 
        PlaylistResponseDTO playlistResponse = PlaylistFactory.createWithResponse();

        when(playlistService.update(playlistResponse.uuid(), playlistRequest))
            .thenReturn(playlistResponse);

        mockMvc.perform(
            put("/playlists/{uuid}", playlistResponse.uuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playlistRequest))    
        )
        .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenFullNameIsBlankOnUpdatePlaylist() throws Exception{
        Playlist playlist = PlaylistFactory.create();
        PlaylistRequestDTO playlistRequest = new PlaylistRequestDTO(
            "",
            5,
            List.of()
        );   
        
        mockMvc.perform(
            put("/playlists/{uuid}", playlist.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playlistRequest))        
        )
        .andExpect(status().isBadRequest());

        verify(playlistService, never()).store(any());
    }

    @Test 
    void shouldDeletePlaylist() throws Exception{
        Playlist playlist = PlaylistFactory.create();
        
        mockMvc.perform(delete("/playlists/{uuid}", playlist.getUuid()))
            .andExpect(status().isOk());

        verify(playlistService).delete(playlist.getUuid());
    }

    @Test
    void shouldAddMusicOnPlaylist() throws Exception{
        PlaylistMusicRequestDTO playlistMusicRequest = PlaylistMusicFactory.createWithRequest();
        List<PlaylistMusicResponseDTO> playlistMusicResponse = PlaylistMusicFactory.createWithResponse(10);
        PlaylistResponseDTO playlistResponse = PlaylistFactory.createWithResponse(playlistMusicResponse);

        when(playlistService.addMusic(playlistResponse.uuid(), playlistMusicRequest))
            .thenReturn(playlistResponse);

        mockMvc.perform(
            post("/playlists/{uuid}/musics", playlistResponse.uuid())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playlistMusicRequest))        
        )
        .andExpect(status().isOk());
    }

    @Test 
    void shouldRemoveMusicOnPlaylist() throws Exception{
        PlaylistMusic playlistMusic = PlaylistMusicFactory.create();
        Playlist playlist = PlaylistFactory.create(playlistMusic);

        mockMvc.perform(
            delete(
                "/playlists/{playlist_uuid}/musics/{music_uuid}",
                playlist.getUuid(),
                playlistMusic.getUuid()
            )
        )
        .andExpect(status().isOk());
    }
}
