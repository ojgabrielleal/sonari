package com.sonari.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.sonari.dto.MediaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class MediaService {

    public MediaResponseDTO  list(){
        Path directory = Path.of("/media");
        return Tree(directory);
    }

    private MediaResponseDTO Tree(Path path){
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
                .map(child -> Tree(child))
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
