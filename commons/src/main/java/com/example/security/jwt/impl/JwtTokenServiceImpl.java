package com.example.security.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenServiceImpl implements JwtTokenService {

    @Override
    public String generateJwtToken(String username, int id, int expirationMs, String secret) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMs);

        return JWT.create()
                .withSubject(username)
                .withClaim("id", id)
                .withIssuedAt(now)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC512(secret));
    }

    @Override
    public String getUserNameFromJwtToken(String token) {
        return JWT.decode(token).getSubject();
    }

    @Override
    public int getUserIdFromJwtToken(String token) {
        return JWT.decode(token).getClaim("id").asInt();
    }

    @Override
    public boolean validateJwtToken(String authToken, String secret) {


        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
