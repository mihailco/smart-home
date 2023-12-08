package com.example.controller;

import com.example.config.Constants;
import com.example.dto.request.AuthorizeRequest;
import com.example.dto.request.DeleteAccountRequest;
import com.example.dto.request.RefreshTokenRequest;
import com.example.dto.request.RegisterRequest;
import com.example.dto.response.TokenResponse;
import com.example.exceptions.InvalidPasswordException;
import com.example.mapper.AuthMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    final private AuthMapper authMapper;

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody RegisterRequest request) {
        return authMapper.register(request);
    }

    @PostMapping("/auth")
    public TokenResponse authorize(@Valid @RequestBody AuthorizeRequest request) throws InvalidPasswordException {
        return authMapper.authorize(request);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshTokenRequest request,
                                 @RequestHeader(Constants.authHeaderName) String accessToken) throws InvalidPasswordException {
        return authMapper.refresh(request, accessToken);
    }

    @PostMapping("/signout")
    public void signout(@RequestHeader(Constants.authHeaderName) String token) {
        authMapper.signout(token);
    }

    @DeleteMapping("/account")
    public void deleteAccount(@RequestHeader(Constants.authHeaderName) String token,
                              @RequestBody DeleteAccountRequest request) throws InvalidPasswordException {
        authMapper.deleteAccount(token, request);
    }
}
