package com.sonari.factories;

import java.util.ArrayList;
import java.util.List;

import com.sonari.entity.PlaylistMusic;

import net.datafaker.Faker;

public class PlaylistMusicFactory {

    private final static Faker faker = new Faker();

    public static PlaylistMusic create(){
        PlaylistMusic playlistMusic = new PlaylistMusic();
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

}
