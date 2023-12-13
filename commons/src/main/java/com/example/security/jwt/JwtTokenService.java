package com.example.security.jwt;

public interface JwtTokenService {
    String generateJwtToken(String username, int id, int expirationMs, String secret);

    String getUserNameFromJwtToken(String token);

    int getUserIdFromJwtToken(String token);

    boolean validateJwtToken(String authToken, String secret);
}
