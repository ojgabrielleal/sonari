package com.sonari.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.dto.MediaResponseDTO;
import com.sonari.service.MediaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medias")
@RequiredArgsConstructor 
@Tag(name="Media", description = "Provides access to media files and directories available in the storage.")
public class MediaController {

    private final MediaService mediaService;

    @GetMapping
    @Operation(
        summary = "List media files in storage",
        description = "Returns all media files available in the storage, including files from subdirectories."
    )
    @SecurityRequirement(name = "bearerAuth")
    public MediaResponseDTO list(){
        return mediaService.list();
    }
}
