package com.sonari.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.dto.MusicRequestDTO;
import com.sonari.dto.MusicResponseDTO;
import com.sonari.service.MusicService;

import lombok.AllArgsConstructor;

@RestController 
@RequestMapping("/musics")
@AllArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping
    public List<MusicResponseDTO> index(){
        return musicService.index();
    }

    @GetMapping("/{uuid}")
    public MusicResponseDTO show(@PathVariable UUID uuid){
        return musicService.show(uuid);
    }

    @PutMapping("/{uuid}")
    public MusicResponseDTO update(@PathVariable UUID uuid, @RequestBody MusicRequestDTO music){
        return musicService.update(uuid, music);
    }

    @DeleteMapping
    public void delete(@PathVariable UUID uuid){
        musicService.delete(uuid);
    }
}
