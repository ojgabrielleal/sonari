package com.sonari.dto;

import java.util.UUID;

public record UserRequestDTO(
    UUID uuid,
    String fullName,
    String nickName
) {}
