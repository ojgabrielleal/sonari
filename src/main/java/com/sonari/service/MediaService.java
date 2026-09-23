package com.sonari.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sonari.dto.MediaResponseDTO;

@Service 
public class MediaService {

    private final Path directory;

    public MediaService(@Value("/media") String directory){
        this.directory = Path.of(directory);
    }

    public MediaResponseDTO index(){
        return tree(directory);
    }

    private MediaResponseDTO tree(Path path){
        if(Files.isRegularFile(path)){
            return new MediaResponseDTO(
                path.getFileName().toString(),
                "file",
                path.toString(),
                null
            );
        }

        try(Stream<Path> paths = Files.list(path)){
            List<MediaResponseDTO> children = paths
                .map(child -> tree(child))
                .toList();

            return new MediaResponseDTO(
                path.getFileName().toString(),
                "directory",
                null,
                children
            );
        }catch(IOException e){
            throw new RuntimeException("Failed to list directory", e);
        }
    }

}
