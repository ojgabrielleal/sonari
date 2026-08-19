package com.sonari.mapper;

import org.springframework.stereotype.Component;

import com.sonari.dto.MusicRequestDTO;
import com.sonari.dto.MusicResponseDTO;
import com.sonari.entity.Music;

@Component
public class MusicMapper {
    
    public MusicResponseDTO toResponse(Music music){
        return new MusicResponseDTO(
            music.getUuid(),
            music.getName(),
            music.getPath()
        );
    }

    public Music toEntity(MusicRequestDTO data){
        Music music = new Music();

        music.setName(data.name());
        music.setPath(data.path());

        return music;
    }

    public void updateEntity(Music music, MusicRequestDTO data){
        music.setName(data.name());
        music.setPath(data.path());
    }
}
