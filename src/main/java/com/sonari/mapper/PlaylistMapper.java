package com.sonari.mapper;

import org.springframework.stereotype.Component;

import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.entity.Playlist;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlaylistMapper {

    private final MusicMapper musicMapper;
    
    public PlaylistResponseDTO toResponse(Playlist playlist){
        return new PlaylistResponseDTO(
            playlist.getUuid(),
            playlist.getName(),
            playlist.getMusics()
                .stream()
                .map(musicMapper::toResponse)
                .toList()
        );
    }

    public Playlist toEntity(PlaylistRequestDTO data){
        Playlist playlist = new Playlist();

        playlist.setName(data.name());

        return playlist;
    }

    public void updateEntity(Playlist playlist, PlaylistRequestDTO data){
        playlist.setName(data.name());
    }
    
}
