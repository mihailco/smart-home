package com.example.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    String accessToken;
    String refreshToken;
    Integer ttl;
}
