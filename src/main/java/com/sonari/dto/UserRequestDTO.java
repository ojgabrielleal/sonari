package com.sonari.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
    @NotBlank(message="Full name is required")
    String fullName,
    @NotBlank(message="Nickname is required")
    String nickName
) {}
