package com.example.security.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceImplTest {

    private final JwtTokenServiceImpl jwtTokenService = new JwtTokenServiceImpl();

    private static final String USERNAME = "testUser";
    private static final int USER_ID = 123;
    private static final int EXPIRATION_MS = 3600000;
    private static final String SECRET = "testSecret";

    @Test
    void generateJwtToken() {
        String token = jwtTokenService.generateJwtToken(USERNAME, USER_ID, EXPIRATION_MS, SECRET);
        assertNotNull(token);
    }

    @Test
    void getUserNameFromJwtToken() {
        String token = generateTestToken();

        String username = jwtTokenService.getUserNameFromJwtToken(token);

        assertEquals(USERNAME, username);
    }

    @Test
    void getUserIdFromJwtToken() {
        String token = generateTestToken();

        int userId = jwtTokenService.getUserIdFromJwtToken(token);

        assertEquals(USER_ID, userId);
    }

    @Test
    void validateJwtToken_ValidToken() {
        String token = generateTestToken();

        boolean isValid = jwtTokenService.validateJwtToken(token, SECRET);

        assertTrue(isValid);
    }

    @Test
    void validateJwtToken_InvalidToken() {
        String invalidToken = "invalidToken";

        boolean isValid = jwtTokenService.validateJwtToken(invalidToken, SECRET);

        assertFalse(isValid);
    }

    private String generateTestToken() {
        return JWT.create()
                .withSubject(USERNAME)
                .withClaim("id", USER_ID)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .sign(Algorithm.HMAC512(SECRET));
    }
}
