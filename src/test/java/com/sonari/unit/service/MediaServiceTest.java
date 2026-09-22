package com.sonari.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sonari.dto.MediaResponseDTO;
import com.sonari.service.MediaService;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {
    
    @TempDir
    Path tempDir;

    @Test
    void shouldListFile() throws IOException {
        Files.createFile(tempDir.resolve("music.mp3"));

        MediaService mediaService = new MediaService(tempDir.toString());

        MediaResponseDTO result = mediaService.list();
        assertEquals(1, result.children().size());

        MediaResponseDTO file = result.children().get(0);
        assertEquals("music.mp3", file.name());
        assertEquals("file", file.type());

    }
}
