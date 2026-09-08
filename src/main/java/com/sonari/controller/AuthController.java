package com.sonari.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.dto.AuthRequestDTO;
import com.sonari.dto.TokenResponseDTO;
import com.sonari.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name="Auth", description = "Handles user authentication and registration.")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates a user with the provided credentials and returns an access token."
    )
    public TokenResponseDTO auth(@RequestBody AuthRequestDTO data){
        return authService.auth(data);
    }

}
