package com.sonari.infrastructure.liquidsoap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sonari.entity.Playlist;
import com.sonari.repository.PlaylistRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class LiquidsoapRotate {

    private final PlaylistRepository playlistRepository;
    
    /**
     * Generates the Liquidsoap rotation configuration using the registered playlists
     * and their respective weights.
     */
    public String generate(){
        Path directory = Path.of("/infrastructure/liquidsoap");
        Path file = directory.resolve("rotate.liq");

        StringBuilder stringBuilder = new StringBuilder();
        List<Playlist> playlists = playlistRepository.findAll();
        
        stringBuilder.append("stream = rotate([\n");

        for(Playlist playlist: playlists){
            String name = "playlist_" + 
                playlist.getUuid()
                    .toString()
                    .replace("-", "_");

            stringBuilder
                .append("    ")
                .append(name)
                .append(".{weight = ")
                .append(playlist.getWeight())
                .append("},\n");

            stringBuilder.append("])\n\n");
        }

        try{
            Files.createDirectories(directory);
            Files.writeString(file, stringBuilder.toString());

            return "%include rotate.liq";
        }catch(IOException e){
            throw new RuntimeException("Failed to generate rotate file", e);
        }
    }

}
