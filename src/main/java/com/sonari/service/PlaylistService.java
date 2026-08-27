package com.sonari.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sonari.dto.MusicRequestDTO;
import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.entity.Music;
import com.sonari.entity.Playlist;
import com.sonari.mapper.MusicMapper;
import com.sonari.mapper.PlaylistMapper;
import com.sonari.repository.MusicRepository;
import com.sonari.repository.PlaylistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    
    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private final MusicRepository musicRepository;
    private final MusicMapper musicMapper;

    public List<PlaylistResponseDTO> index(){
        return playlistRepository.findAll()
            .stream()
            .map(playlistMapper::toResponse)
            .toList();
    }

    public PlaylistResponseDTO show(UUID uuid){
        return playlistMapper.toResponse(
            playlistRepository.findByUuid(uuid).orElseThrow()
        );
    }

    public PlaylistResponseDTO store(PlaylistRequestDTO data){
        Playlist playlist = playlistRepository.save(
            playlistMapper.toEntity(data)
        );       

        return playlistMapper.toResponse(playlist);
    }

    public PlaylistResponseDTO update(UUID uuid, PlaylistRequestDTO data){
        Playlist playlist = playlistRepository.findByUuid(uuid).orElseThrow();
        
        playlistMapper.updateEntity(playlist, data);
        
        return playlistMapper.toResponse(
            playlistRepository.save(playlist)
        );
    }

    public void delete(UUID uuid){
        Playlist playlist = playlistRepository.findByUuid(uuid).orElseThrow();
        
        playlistRepository.delete(playlist);
    }

    public PlaylistResponseDTO addMusic(UUID uuid, MusicRequestDTO music){
        Playlist playlist = playlistRepository.findByUuid(uuid).orElseThrow();

        playlist.getMusics().add(
            musicMapper.toEntity(music)
        );

        return playlistMapper.toResponse(playlist);
    }

    public void deleteMusic(UUID playlistUUID, UUID musicUUID){
        Playlist playlist = playlistRepository.findByUuid(playlistUUID).orElseThrow();
        
        Music music = musicRepository.findByUuid(musicUUID).orElseThrow();

        playlist.getMusics().remove(music);
    }

}
