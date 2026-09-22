package com.sonari.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaylistMusicRequestDTO(
    @NotBlank(message="name is required")
    String name,
    @NotBlank(message="path is required")
    String path
){}
