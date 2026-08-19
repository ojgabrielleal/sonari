package com.sonari.dto;

import java.util.List;
import java.util.UUID;

public record PlaylistResponseDTO(
    UUID uuid, 
    String name,
    List<MusicResponseDTO> musics
){}
