package com.sonari.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlaylistRequestDTO(
    @NotBlank(message="name is required")
    String name,
    @NotNull(message="weight is required")
    Integer weight,
    List<PlaylistMusicRequestDTO> musics
){}
