package com.example.security.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private final String accessSecret = "qweqweerkbbloug";

    private final int accessTtl = 600000;

    private final String refreshSecret = "huiwiwjiwdjiwdhuiwg";

    private final int refreshTtl = 1728000000;
}
