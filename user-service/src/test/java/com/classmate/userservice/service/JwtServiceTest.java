package com.classmate.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;

// ============================================================
// PURPOSE: Unit tests for JWT token generation
// ============================================================
@ActiveProfiles("test")
public class JwtServiceTest {

    private JwtService jwtService;

    private final String testSecret = "thisIsA32ByteLongSecretForTesting!!";
    private final long testExpirationMs = 1000 * 60 * 60; // 1 hour in ms

    @BeforeEach
    void setUp() {
        // Instantiate JwtService with test secret and expiration
        jwtService = new JwtService(testSecret, testExpirationMs);
    }

    @Test
    void testGenerateTokenNotNull() {
        // Arrange
        String subject = "john@example.com";

        // Act
        String token = jwtService.generateToken(subject);

        // Assert
        assertThat(token).isNotNull().isNotEmpty();
    }

    @Test
    void testGenerateTokenContainsSubject() {
        // Arrange
        String subject = "john@example.com";

        // Act
        String token = jwtService.generateToken(subject);

        // Decode the token manually for verification
        Key signingKey = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Assert
        assertThat(claims.getSubject()).isEqualTo(subject);
    }

    @Test
    void testGenerateTokenExpiration() {
        // Arrange
        String subject = "john@example.com";
        long now = System.currentTimeMillis();

        // Act
        String token = jwtService.generateToken(subject);

        // Decode the token manually for verification
        Key signingKey = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // Assert expiration is within expected range
        assertThat(claims.getExpiration().getTime()).isBetween(now + testExpirationMs - 1000, now + testExpirationMs + 1000);
    }
}