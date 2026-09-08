package com.sonari.dto;

import java.util.UUID;

public record PlaylistMusicResponseDTO(
    UUID uuid,
    String name,
    String path
){}
