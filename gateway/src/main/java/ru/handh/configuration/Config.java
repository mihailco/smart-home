package ru.handh.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/homes/**")
                        .uri("http://localhost:8081"))
                .route(p -> p
                        .path("/api/rooms/**")
                        .uri("http://localhost:8081"))
                .route(p -> p
                        .path("/api/register")
                        .uri("http://localhost:8084"))
                .route(p -> p
                        .path("/api/auth")
                        .uri("http://localhost:8084"))
                .route(p -> p
                        .path("/api/refresh")
                        .uri("http://localhost:8084"))
                .route(p -> p
                        .path("/api/signout")
                        .uri("http://localhost:8084"))
                .build();
    }
}
