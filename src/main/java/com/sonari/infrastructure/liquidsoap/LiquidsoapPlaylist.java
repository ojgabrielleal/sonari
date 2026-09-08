package com.sonari.infrastructure.liquidsoap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.sonari.entity.Playlist;
import com.sonari.entity.PlaylistMusic;
import com.sonari.repository.PlaylistRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class LiquidsoapPlaylist {

    private final PlaylistRepository playlistRepository;

    public String generate() throws IOException{
        List<Playlist> playlists = playlistRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();

        Path directory = Path.of("/infrastructure/liquidsoap");
        Path file = directory.resolve("playlists.liq");

        for(Playlist playlist: playlists){
            generateM3uPlaylist(playlist);

            String name = "playlist_" + 
                playlist.getUuid()
                    .toString()
                    .replace("-", "_");

            stringBuilder
                .append(name)
                .append(" =playlist(\"/infrastructure/liquidsoap/m3u/)")
                .append(playlist.getUuid())
                .append(".m3u\")\n");
        }

        try{
            Files.createDirectories(directory);
            Files.writeString(file, stringBuilder.toString());

            return "%include playlist.liq";
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate playlists.liq", e);
        }
    }

    /**
     * Generates an M3U playlist file containing the paths of all tracks
     * associated with the provided playlist.
     */
    private void generateM3uPlaylist(Playlist playlist) throws IOException{
        Path directory = Path.of("/infrastructure/liquidsoap/m3u");

        try(Stream<Path> files = Files.list(directory)){
            for(Path file: files.toList()){
                if(file.toString().endsWith(".m3u")){
                    Files.delete(file);
                }
            }
        }
        
        StringBuilder stringBuilder = new StringBuilder();

        for(PlaylistMusic music: playlist.getMusics()){
            stringBuilder.append(music.getPath()).append("\n");
        }
        
        Path file = directory.resolve(playlist.getUuid() + ".m3u");
        
        try{
            Files.createDirectories(directory);
            Files.writeString(file, stringBuilder.toString());
        }catch(IOException e){
            throw new RuntimeException("Failed to generate M3U playlist file", e);
        }
    }
}
