package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthorizeRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
