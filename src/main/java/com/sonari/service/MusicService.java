package com.sonari.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sonari.dto.MusicRequestDTO;
import com.sonari.dto.MusicResponseDTO;
import com.sonari.entity.Music;
import com.sonari.mapper.MusicMapper;
import com.sonari.repository.MusicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusicService {
    
    private final MusicRepository musicRepository;
    private final MusicMapper musicMapper;

    public List<MusicResponseDTO> index(){
        return musicRepository.findAll()
            .stream()
            .map(musicMapper::toResponse)
            .toList();
    }

    public MusicResponseDTO show(UUID uuid){
        return musicMapper.toResponse(
            musicRepository.findByUuid(uuid).orElseThrow()
        );
    } 

    public Music update(UUID uuid, MusicRequestDTO data){
        Music music = musicRepository.findByUuid(uuid).orElseThrow();

        musicMapper.updateEntity(music, data);

        return musicRepository.save(music);
    }

    public void delete(UUID uuid){
        Music music = musicRepository.findByUuid(uuid).orElseThrow(); 
        
        musicRepository.delete(music);
    }


}
