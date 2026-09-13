package com.sonari.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonari.dto.PlaylistMusicRequestDTO;
import com.sonari.dto.PlaylistMusicResponseDTO;
import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.entity.Playlist;
import com.sonari.entity.PlaylistMusic;
import com.sonari.factories.PlaylistFactory;
import com.sonari.factories.PlaylistMusicFactory;
import com.sonari.mapper.PlaylistMusicMapper;
import com.sonari.mapper.PlaylistMapper;
import com.sonari.repository.PlaylistMusicRepository;
import com.sonari.repository.PlaylistRepository;
import com.sonari.service.PlaylistService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {
    
    @Mock
    private PlaylistRepository playlistRepository;
    @Mock 
    private PlaylistMapper playlistMapper;
    @Mock 
    private PlaylistMusicRepository playlistMusicRepository;
    @Mock 
    private PlaylistMusicMapper playlistMusicMapper;
    @InjectMocks 
    private PlaylistService playlistService;

    private final static Faker faker = new Faker();

    @Test
    void shouldRetunsAllPlaylists(){
        List<Playlist> playlists = PlaylistFactory.create(5);
        
        when(playlistRepository.findAll())
            .thenReturn(playlists);

        for(Playlist playlist: playlists){
            PlaylistResponseDTO playlistResponse = new PlaylistResponseDTO(
                playlist.getUuid(), 
                playlist.getName(), 
                playlist.getWeight(), 
                List.of()
            );

            when(playlistMapper.toResponse(playlist))
                .thenReturn(playlistResponse);
        }

        List<PlaylistResponseDTO> result = playlistService.index();
        assertEquals(5, result.size());
    }

    @Test 
    void shouldReturnUniquePlaylist(){
        Playlist playlist = PlaylistFactory.create();

        PlaylistResponseDTO playlistResponse = new PlaylistResponseDTO(
            playlist.getUuid(), 
            playlist.getName(), 
            playlist.getWeight(), 
            List.of()
        );

        when(playlistRepository.findByUuid(playlist.getUuid()))
            .thenReturn(Optional.of(playlist));

        when(playlistMapper.toResponse(playlist))
            .thenReturn(playlistResponse);

        PlaylistResponseDTO result = playlistService.show(playlist.getUuid());

        assertEquals(playlist.getUuid(), result.uuid());
        assertEquals(playlist.getName(), result.name());
        assertEquals(playlist.getWeight(), result.weight());
    }

    @Test
    void shouldStorePlaylist(){
        Playlist playlist = PlaylistFactory.create();

        PlaylistRequestDTO playlistRequest = new PlaylistRequestDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.number().randomDigitNotZero(),
            List.of()
        );

        PlaylistResponseDTO playlistResponse = new PlaylistResponseDTO(
            playlist.getUuid(), 
            playlist.getName(), 
            playlist.getWeight(), 
            List.of()
        );

        when(playlistRepository.save(playlist))
            .thenReturn(playlist);

        when(playlistMapper.toEntity(playlistRequest))
            .thenReturn(playlist);

        when(playlistMapper.toResponse(playlist))
            .thenReturn(playlistResponse);

        PlaylistResponseDTO result = playlistService.store(playlistRequest);

        assertEquals(playlist.getUuid(), result.uuid());
        assertEquals(playlist.getName(), result.name());
        assertEquals(playlist.getWeight(), result.weight());
    }

    @Test 
    void shouldUpdatePlaylist(){
        Playlist playlist = PlaylistFactory.create();

        PlaylistRequestDTO playlistRequest = new PlaylistRequestDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.number().randomDigitNotZero(),
            List.of()
        );

        PlaylistResponseDTO playlistResponse = new PlaylistResponseDTO(
            playlist.getUuid(), 
            playlist.getName(), 
            playlist.getWeight(), 
            List.of()
        );

        when(playlistRepository.findByUuid(playlist.getUuid()))
            .thenReturn(Optional.of(playlist));

        when(playlistRepository.save(playlist))
        .thenReturn(playlist);

        when(playlistMapper.toResponse(playlist))
            .thenReturn(playlistResponse);

        PlaylistResponseDTO result = playlistService.update(playlist.getUuid(), playlistRequest);

        verify(playlistMapper).updateEntity(playlist, playlistRequest);

        assertEquals(playlist.getUuid(), result.uuid());
        assertEquals(playlist.getName(), result.name());
        assertEquals(playlist.getWeight(), result.weight());
    }

    @Test 
    void shouldDeletePlaylist(){
        Playlist playlist = PlaylistFactory.create();

        when(playlistRepository.findByUuid(playlist.getUuid()))
            .thenReturn(Optional.of(playlist));

        playlistService.delete(playlist.getUuid());

        verify(playlistRepository).delete(playlist);
    }

    @Test
    void shouldAddMusicOnOnePlaylist(){
        Playlist playlist = PlaylistFactory.create();
        PlaylistMusic playlistMusic = PlaylistMusicFactory.create();
        
        PlaylistMusicRequestDTO playlistMusicRequest = new PlaylistMusicRequestDTO(
            faker.name().fullName(),
            "file/" + faker.internet().uuid() + ".mp3"
        );

        PlaylistMusicResponseDTO playlistMusicResponse = new PlaylistMusicResponseDTO(
            playlistMusic.getUuid(),
            playlistMusic.getName(),
            playlistMusic.getPath()
        ); 

        when(playlistRepository.findByUuid(playlist.getUuid()))
            .thenReturn(Optional.of(playlist));

        when(playlistMusicMapper.toEntity(playlistMusicRequest))
            .thenReturn(playlistMusic);

        when(playlistMusicMapper.toResponse(playlistMusic))
            .thenReturn(playlistMusicResponse);

        when(playlistMapper.toResponse(playlist))
            .thenAnswer(invocation -> 
                new PlaylistResponseDTO(
                    playlist.getUuid(), 
                    playlist.getName(), 
                    playlist.getWeight(), 
                    playlist.getMusics()
                        .stream()
                        .map(playlistMusicMapper::toResponse)
                        .toList()
                )
            );

        PlaylistResponseDTO result = playlistService.addMusic(playlist.getUuid(), playlistMusicRequest);

        assertEquals(1, result.musics().size());
    }

    @Test 
    void shouldDeleteMuiscOnPlaylist(){
        Playlist playlist = PlaylistFactory.create();
        PlaylistMusic playlistMusic = PlaylistMusicFactory.create();

        playlist.getMusics().add(playlistMusic);

        when(playlistRepository.findByUuid(playlist.getUuid()))
            .thenReturn(Optional.of(playlist));

        when(playlistMusicRepository.findByUuid(playlistMusic.getUuid()))
            .thenReturn(Optional.of(playlistMusic));

        playlistService.deleteMusic(playlist.getUuid(), playlistMusic.getUuid());

        assertEquals(0, playlist.getMusics().size());
    }
}
