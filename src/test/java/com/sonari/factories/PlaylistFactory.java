package com.sonari.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.sonari.dto.PlaylistMusicResponseDTO;
import com.sonari.dto.PlaylistRequestDTO;
import com.sonari.dto.PlaylistResponseDTO;
import com.sonari.entity.Playlist;
import com.sonari.entity.PlaylistMusic;

import net.datafaker.Faker;

public class PlaylistFactory {

    private final static Faker faker = new Faker();

    public static Playlist create(){
        Playlist playlist = new Playlist();
        playlist.setUuid(UUID.randomUUID());
        playlist.setName(faker.name().fullName());
        playlist.setWeight(faker.number().randomDigitNotZero());

        return playlist;
    }

    public static Playlist create(PlaylistMusic playlistMusic){
        Playlist playlist = new Playlist();
        playlist.setUuid(UUID.randomUUID());
        playlist.setName(faker.name().fullName());
        playlist.setWeight(faker.number().randomDigitNotZero());
        playlist.getMusics().add(playlistMusic);

        return playlist;
    }

    public static List<Playlist> create(int quantity){
        List<Playlist> playlists = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            playlists.add(create());
        }

        return playlists;
    }

    public static PlaylistResponseDTO createWithResponse(){
        return new PlaylistResponseDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.number().randomDigitNotZero(),
            List.of()
        );
    }
    
    
    public static PlaylistResponseDTO createWithResponse(List<PlaylistMusicResponseDTO> playlistMusicsDTO){
        return new PlaylistResponseDTO(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.number().randomDigitNotZero(),
            playlistMusicsDTO
        );
    }

    public static PlaylistResponseDTO createWithResponse(Playlist playlist){
         return new PlaylistResponseDTO(
            playlist.getUuid(),
            playlist.getName(),
            playlist.getWeight(),
            List.of()
        );
    }

    public static List<PlaylistResponseDTO> createWithResponse(int quantity){
        List<PlaylistResponseDTO> playlists = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            playlists.add(createWithResponse());
        }

        return playlists;
    }

    public static PlaylistRequestDTO createWithRequest(){
        return new PlaylistRequestDTO(
            faker.name().fullName(),
            faker.number().randomDigitNotZero(),
            List.of()
        );        
    }
}
