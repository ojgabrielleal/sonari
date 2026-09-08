package com.sonari.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.sonari.infrastructure.liquidsoap.LiquidsoapOutput;
import com.sonari.infrastructure.liquidsoap.LiquidsoapPlaylist;
import com.sonari.infrastructure.liquidsoap.LiquidsoapRotate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Liquidsoap {

    private final LiquidsoapPlaylist liquidsoapPlaylist;
    private final LiquidsoapRotate liquidsoapRotate;
    private final LiquidsoapOutput liquidsoapOutput;

    /**
     * Generates the Liquidsoap configuration file and restarts the Liquidsoap service.
     *
     * <p>The configuration is generated based on the current playlists and output
     * settings, then written to {@code /infrastructure/liquidsoap/liquidsoap.liq}.</p>
     */
    public void generateConfiguration(){
        Path file = Path.of("/infrastructure/liquidsoap/liquidsoap.liq");

        StringBuilder stringBuilder = new StringBuilder();

        try{ 
            stringBuilder.append(liquidsoapPlaylist.generate());
            stringBuilder.append(liquidsoapRotate.generate());
            stringBuilder.append(liquidsoapOutput.generate());

            Files.writeString(file, stringBuilder.toString());
            new ProcessBuilder("systemctl", "restart", "liquidsoap").start();
        }catch(IOException e){
            throw new RuntimeException("Failed to write Liquidsoap configuration to liquidsoap.liq", e);
        }
    }
}
