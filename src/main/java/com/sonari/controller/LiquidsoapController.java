package com.sonari.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.infrastructure.Liquidsoap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/liquidsoap")
@RequiredArgsConstructor
@Tag(name="Liquidsoap", description = "Handles controllers to manage liquidsoap")
public class LiquidsoapController {
    
    private final Liquidsoap liquidsoap;

    @PostMapping
    @Operation(
        summary = "Generate Liquidsoap configuration",
        description = "Generates the radio.liq configuration file used by Liquidsoap."
    )
    @SecurityRequirement(name = "bearerAuth")
    public void build(){
        liquidsoap.generateConfiguration();
    }

}
