package com.sonari.factories;

import java.util.ArrayList;
import java.util.List;

import com.sonari.entity.Playlist;

import net.datafaker.Faker;

public class PlaylistFactory {

    private final static Faker faker = new Faker();

    public static Playlist create(){
        Playlist playlist = new Playlist();
        playlist.setName(faker.name().fullName());
        playlist.setWeight(faker.number().randomDigitNotZero());

        return playlist;
    }

    public static List<Playlist> create(int quantity){
        List<Playlist> playlists = new ArrayList<>();

        for(int i = 0; i < quantity; i++){
            playlists.add(create());
        }

        return playlists;
    }
}
