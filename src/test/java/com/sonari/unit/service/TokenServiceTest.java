package com.sonari.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.sonari.entity.User;
import com.sonari.factories.UserFactory;
import com.sonari.service.TokenService;

public class TokenServiceTest {

    private final String secret = "f8K2xQ9mV4zL7pN1cR6wY3tH0jS5aB8uD2eG9kM4qX7vP1nC";
    private final TokenService tokenService = new TokenService(secret);

    @Test
    void shouldGenerateToken() {
        User user = UserFactory.create();
        
        String token = tokenService.generateToken(user);

        assertNotNull(token);

        String username = tokenService.validateToken(token);

        assertEquals(user.getUsername(), username);
        
    }
}

