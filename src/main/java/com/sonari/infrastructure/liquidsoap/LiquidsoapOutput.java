package com.sonari.infrastructure.liquidsoap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class LiquidsoapOutput {
    
    @Value("${ICECAST_SOURCE_PASSWORD}")
    private String icecastSourcePassword;

    /**
     * Appends the Icecast output configuration to the Liquidsoap configuration.
    */
    public String generate(){
        Path directory = Path.of("radio/liquidsoap");
        Path file = directory.resolve("output.liq");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
            .append("output.icecast(\n")
            .append("    %mp3(bitrate=128),\n")
            .append("    host=\"icecast\",\n")
            .append("    port=8000,\n")
            .append("    password=\"")
            .append(icecastSourcePassword)
            .append("\",\n")
            .append("    mount=\"/stream.mp3\",\n")
            .append("    stream\n")
            .append(")\n");

        try{
            Files.createDirectories(directory);
            Files.writeString(file, stringBuilder.toString());

            return "%include output.liq";
        }catch(IOException e){
            throw new RuntimeException("Failed to generate output file", e);
        }
    }
}
