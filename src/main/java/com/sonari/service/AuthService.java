package com.sonari.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sonari.dto.AuthRequestDTO;
import com.sonari.dto.TokenResponseDTO;
import com.sonari.entity.User;
import com.sonari.repository.UserRepository;
import com.sonari.security.TokenService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class AuthService {
    
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public TokenResponseDTO auth(AuthRequestDTO data){
        User user = userRepository
            .findByUsername(data.username())
            .orElseThrow();

        boolean passwordMatches = passwordEncoder.matches(
            data.password(),
            user.getPassword()
        );

        if(!passwordMatches){
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generateToken(user);

        return new TokenResponseDTO(token);

    }

    public void register(AuthRequestDTO data){
        User user = new User();

        user.setUsername(data.username());
        user.setPassword(
            passwordEncoder.encode(data.password())
        );

        userRepository.save(user);
    }
}
