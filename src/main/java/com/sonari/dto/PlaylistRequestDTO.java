package com.sonari.dto;

import java.util.List;
import java.util.UUID;

public record PlaylistRequestDTO(
    UUID uuid, 
    String name,
    List<MusicRequestDTO> musics
){}
