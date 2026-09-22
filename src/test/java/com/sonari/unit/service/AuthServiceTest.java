package com.sonari.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sonari.dto.AuthRequestDTO;
import com.sonari.dto.TokenResponseDTO;
import com.sonari.entity.User;
import com.sonari.factories.UserFactory;
import com.sonari.repository.UserRepository;
import com.sonari.service.AuthService;
import com.sonari.service.TokenService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    
    @Mock 
    private TokenService tokenService;
    @Mock 
    private PasswordEncoder passwordEncoder;
    @Mock 
    private UserRepository userRepository;
    @InjectMocks 
    private AuthService authService;

    private final static Faker faker = new Faker();

    @Test 
    void shouldAuthenticateUser(){
        User user = UserFactory.create();

        String rawToken = faker.internet().uuid();
        String rawPassword = faker.credentials().password();

        when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(rawPassword, user.getPassword()))
            .thenReturn(true);

        when(tokenService.generateToken(user))
            .thenReturn(rawToken);

        TokenResponseDTO result = authService.auth(
            new AuthRequestDTO(
                user.getUsername(),
                rawPassword
            )
        );

        assertEquals(rawToken, result.token());
    }
}
