package com.sonari.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sonari.dto.PlaylistMusicRequestDTO;
import com.sonari.dto.PlaylistMusicResponseDTO;
import com.sonari.entity.PlaylistMusic;

import net.datafaker.Faker;

public class PlaylistMusicFactory {

    private final static Faker faker = new Faker();

    public static PlaylistMusic create(){
        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.setUuid(UUID.randomUUID());
        playlistMusic.setName(faker.name().fullName());
        playlistMusic.setPath("file/" + faker.internet().uuid() + ".mp3");

        return playlistMusic;
    }

    public static List<PlaylistMusic> create(int quantity){
        List<PlaylistMusic> playlistMusics = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            playlistMusics.add(create());
        }

        return playlistMusics;
    }

    public static PlaylistMusicResponseDTO createWithResponse(){
        return new PlaylistMusicResponseDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            "file/" + faker.internet().uuid() + ".mp3"  
        );
    }

    public static PlaylistMusicResponseDTO createWithResponse(PlaylistMusic playlistMusic){
        return new PlaylistMusicResponseDTO(
            playlistMusic.getUuid(),
            playlistMusic.getName(),
            playlistMusic.getPath()
        ); 
    }
    
    public static List<PlaylistMusicResponseDTO> createWithResponse(int quantity){
        List<PlaylistMusicResponseDTO> playlistMusics = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            playlistMusics.add(createWithResponse());
        }

        return playlistMusics;
    }

    public static PlaylistMusicRequestDTO createWithRequest(){
        return new PlaylistMusicRequestDTO(
            faker.name().fullName(),
            "file/" + faker.internet().uuid() + ".mp3"
        );
    }

    public static List<PlaylistMusicRequestDTO> createWithRequest(int quantity){
        List<PlaylistMusicRequestDTO> playlistMusics = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            playlistMusics.add(createWithRequest());
        }

        return playlistMusics;
    }
}
