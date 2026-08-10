package com.sonari.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sonari.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
    
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user){
        SecretKey key = Keys.hmacShaKeyFor(
            secret.getBytes()
        );

        Date now = new Date();
        Date expiration = new Date(
            System.currentTimeMillis() + 1000 * 60 * 60
        );

        return Jwts.builder()
            .subject(user.getUsername())
            .signWith(key)
            .issuedAt(now)
            .expiration(expiration)
            .compact();
    }

    public String validateToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(
            secret.getBytes()
        );

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
