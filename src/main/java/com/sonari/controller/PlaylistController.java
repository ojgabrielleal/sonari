package com.sonari.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.dto.MusicRequestDTO;
import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.service.PlaylistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    
    private final PlaylistService playlistService;

    @GetMapping
    public List<PlaylistResponseDTO> index(){
        return playlistService.index();
    }

    @GetMapping("/{uuid}")
    public PlaylistResponseDTO show(@PathVariable UUID uuid){
        return playlistService.show(uuid);
    }

    @PostMapping
    public PlaylistResponseDTO store(@RequestBody PlaylistRequestDTO playlist){
        return playlistService.store(playlist);
    }

    @PutMapping("/{uuid}")
    public PlaylistResponseDTO update(@PathVariable UUID uuid, @RequestBody PlaylistRequestDTO playlist){
        return playlistService.update(uuid, playlist);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        playlistService.delete(uuid);
    }

    @PostMapping("/{uuid}")
    public PlaylistResponseDTO addMusic(@PathVariable UUID uuid, @RequestBody MusicRequestDTO music){
        return playlistService.addMusic(uuid, music);
    }

    @DeleteMapping("/{playlistUUID}/{musicUUID}") 
    public void deleteMusic(@PathVariable UUID playlistUUID, @PathVariable UUID musicUUID){
        playlistService.deleteMusic(playlistUUID, musicUUID);
    }

}
