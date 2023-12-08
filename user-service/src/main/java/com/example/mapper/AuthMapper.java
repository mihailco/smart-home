package com.example.mapper;

import com.example.dto.request.DeleteAccountRequest;
import com.example.exceptions.InvalidPasswordException;
import com.example.exceptions.NotFoundException;
import com.example.security.filter.UserContextHolder;
import com.example.service.UserTokenService;
import com.example.dto.request.AuthorizeRequest;
import com.example.dto.request.RefreshTokenRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.TokenResponse;
import com.example.entity.UserEntity;
import com.example.entity.UserTokenEntity;
import com.example.security.jwt.JwtUtils;
import com.example.security.jwt.TokenType;
import com.example.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class AuthMapper {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserService userService;
    private final UserTokenService tokenService;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse register(RegisterRequest request) {
        UserEntity user = modelMapper.map(request, UserEntity.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userService.register(user);
        return createSaveTokenResponse(user);
    }

    public TokenResponse authorize(AuthorizeRequest request) throws InvalidPasswordException {
        UserEntity user = userService.getByUsername(request.getUsername());
        if (user == null) {
            throw new NotFoundException("user not found");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("invalid password");
        }
        return createSaveTokenResponse(user);
    }

    public TokenResponse refresh(RefreshTokenRequest request, String accessToken) throws InvalidPasswordException {
        if (tokenService.deleteByRefreshToken(request.getRefreshToken()) == 0) {
            throw new InvalidPasswordException("invalid token");
        }
        int id = jwtUtils.getIdFromJwtToken(accessToken);

        UserEntity user = entityManager.getReference(UserEntity.class, id);
        return createSaveTokenResponse(user);
    }

    public void signout(String token) {
        tokenService.deleteByAccessToken(token);
    }

    private TokenResponse createSaveTokenResponse(UserEntity user) {
        String username = user.getUsername();
        String access = jwtUtils.generateJwt(username, user.getId(), TokenType.ACCESS);
        String refresh = jwtUtils.generateJwt(username, user.getId(), TokenType.REFRESH);
        int ttl = jwtUtils.getJwtProperties().getRefreshTtl();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, ttl);
        UserTokenEntity userToken = UserTokenEntity.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .expiredAt(calendar.getTime())
                .user(user)
                .build();

        tokenService.create(userToken);

        return TokenResponse.builder().accessToken(access).refreshToken(refresh).ttl(jwtUtils.getJwtProperties().getAccessTtl()).build();
    }

    public void deleteAccount(String token, DeleteAccountRequest request) throws InvalidPasswordException {
        UserEntity user = userService.getById(UserContextHolder.getId());
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("user not found");
        }

        userService.deleteById(user.getId());
    }
}
