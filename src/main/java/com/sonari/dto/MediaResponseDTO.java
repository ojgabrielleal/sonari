package com.sonari.dto;

import java.util.List;

public record MediaResponseDTO(
    String name,
    String type,
    String path,
    List<MediaResponseDTO> children
){}