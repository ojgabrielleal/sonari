package com.sonari.mapper;

import org.springframework.stereotype.Component;

import com.sonari.dto.PlaylistMusicRequestDTO;
import com.sonari.dto.PlaylistMusicResponseDTO;
import com.sonari.entity.PlaylistMusic;

@Component
public class PlaylistMusicMapper {
    
    public PlaylistMusicResponseDTO toResponse(PlaylistMusic music){
        return new PlaylistMusicResponseDTO(
            music.getUuid(),
            music.getName(),
            music.getPath()
        );
    }

    public PlaylistMusic toEntity(PlaylistMusicRequestDTO data){
        PlaylistMusic music = new PlaylistMusic();

        music.setName(data.name());
        music.setPath(data.path());

        return music;
    }

    public void updateEntity(PlaylistMusic music, PlaylistMusicRequestDTO data){
        music.setName(data.name());
        music.setPath(data.path());
    }
}
