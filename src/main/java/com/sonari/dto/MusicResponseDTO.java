package com.sonari.dto;

import java.util.UUID;

public record MusicResponseDTO(
    UUID uuid,
    String name,
    String path
){}
