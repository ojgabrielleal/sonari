package com.sonari.dto;

import java.util.UUID;

public record MusicRequestDTO(
    UUID uuid,
    String name,
    String path
){}
