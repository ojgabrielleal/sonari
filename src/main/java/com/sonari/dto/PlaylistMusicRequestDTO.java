package com.sonari.dto;

import java.util.UUID;

public record PlaylistMusicRequestDTO(
    UUID uuid,
    String name,
    String path
){}
