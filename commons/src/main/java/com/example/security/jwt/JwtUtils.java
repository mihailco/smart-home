package com.example.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    private final JwtTokenService jwtTokenService;

    public String generateJwt(String username, int id, TokenType tokenType) {
        String secret = "";
        int ttl = 0;

        if (tokenType == TokenType.ACCESS) {
            secret = jwtProperties.getAccessSecret();
            ttl = jwtProperties.getAccessTtl();
        } else if (tokenType == TokenType.REFRESH) {
            secret = jwtProperties.getRefreshSecret();
            ttl = jwtProperties.getRefreshTtl();
        }

        return jwtTokenService.generateJwtToken(username, id, ttl, secret);
    }


    public boolean validateJwtToken(String authToken, TokenType tokenType) {
        String secret = "";

        if (tokenType == TokenType.ACCESS) {
            secret = jwtProperties.getAccessSecret();
        } else if (tokenType == TokenType.REFRESH) {
            secret = jwtProperties.getRefreshSecret();
        }

        return jwtTokenService.validateJwtToken(authToken, secret);
    }

    public String getUsernameFromJwtToken(String token) {
        return jwtTokenService.getUserNameFromJwtToken(token);
    }

    public int getIdFromJwtToken(String token) {
        return jwtTokenService.getUserIdFromJwtToken(token);
    }

}