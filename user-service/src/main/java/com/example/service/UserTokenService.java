package com.example.service;

import com.example.entity.UserTokenEntity;
import com.example.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserTokenService {

    final private UserTokenRepository repository;

    public UserTokenEntity create(UserTokenEntity entity) {
        return repository.save(entity);
    }

    public void deleteExpiredTokens() {
        repository.deleteAllByExpiredAtBefore(new Date());
    }

    public int deleteByRefreshToken(String refreshToken){
        return repository.deleteByRefreshToken(refreshToken);
    }

    public void deleteByAccessToken(String accessToken){
        repository.deleteByAccessToken(accessToken);
    }
}
