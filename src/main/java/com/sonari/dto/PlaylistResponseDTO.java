package com.sonari.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record PlaylistResponseDTO(
    UUID uuid, 
    String name,
    Map<String, Object> liquidsoap_config,
    List<MusicResponseDTO> musics
){}
