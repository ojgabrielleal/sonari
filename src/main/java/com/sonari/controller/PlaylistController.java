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

import com.sonari.dto.PlaylistMusicRequestDTO;
import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.service.PlaylistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
@Tag(name = "Playlists", description = "Handles playlist management and music organization.")
public class PlaylistController {
    
    private final PlaylistService playlistService;

    @GetMapping
    @Operation(
        summary = "List all playlists",
        description = "Returns a list of all registered playlists."
    )
    @SecurityRequirement(name = "bearerAuth")
    public List<PlaylistResponseDTO> index(){
        return playlistService.index();
    }

    @GetMapping("/{playlist_uuid}")
    @Operation(
        summary = "Get playlist by uuid",
        description = "Returns a playlist based on the provided UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public PlaylistResponseDTO show(@PathVariable("playlist_uuid") UUID uuid){
        return playlistService.show(uuid);
    }

    @PostMapping
    @Operation(
        summary = "Create playlist",
        description = "Creates a new playlist with the provided data."
    )
    @SecurityRequirement(name = "bearerAuth")
    public PlaylistResponseDTO store(@RequestBody PlaylistRequestDTO playlist){
        return playlistService.store(playlist);
    }

    @PutMapping("/{playlist_uuid}")
    @Operation(
        summary = "Update playlist",
        description = "Updates an existing playlist based on the provided UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public PlaylistResponseDTO update(@PathVariable("playlist_uuid") UUID uuid, @RequestBody PlaylistRequestDTO playlist){
        return playlistService.update(uuid, playlist);
    }

    @DeleteMapping("/{playlist_uuid}")
    @Operation(
        summary = "Delete playlist",
        description = "Deletes an existing playlist based on the provided UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public void delete(@PathVariable("uuid") UUID uuid){
        playlistService.delete(uuid);
    }

    @PostMapping("/{playlist_uuid}/musics")
    @Operation(
        summary = "Add music to playlist",
        description = "Adds a music to a playlist based on the provided playlist UUID."
    )
    @SecurityRequirement(name = "bearerAuth")
    public PlaylistResponseDTO addMusic(@PathVariable("playlist_uuid") UUID uuid, @RequestBody PlaylistMusicRequestDTO music){
        return playlistService.addMusic(uuid, music);
    }

    @DeleteMapping("/{playlist_uuid}/musics/{music_uuid}") 
    @Operation(
        summary = "Remove music from playlist",
        description = "Removes a music from a playlist based on the provided playlist and music UUIDs."
    )
    @SecurityRequirement(name = "bearerAuth")
    public void deleteMusic(@PathVariable("playlist_uuid") UUID playlistUuid, @PathVariable("music_uuid") UUID musicUuid){
        playlistService.deleteMusic(playlistUuid, musicUuid);
    }

}
