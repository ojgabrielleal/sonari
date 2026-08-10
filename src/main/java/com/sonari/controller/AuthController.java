package com.sonari.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sonari.dto.AuthRegisterDTO;
import com.sonari.dto.AuthRequestDTO;
import com.sonari.dto.AuthResponseDTO;
import com.sonari.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public AuthResponseDTO auth(@RequestBody AuthRequestDTO data){
        return authService.auth(data);
    }

    @PostMapping("/register")
    public void register(@RequestBody AuthRegisterDTO data){
        authService.register(data);
    }

}
