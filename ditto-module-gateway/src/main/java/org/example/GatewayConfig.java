package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator ms1Route(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userAPI", r -> r.path("/api/members/**", "/api/auth/**", "/api/mypage/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8080")
                )
                .route("activityAPI", r -> r.path("/api/posts/**", "/api/follow/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8081")
                )
                .route("newsfeedAPI", r -> r.path("/api/newsfeed/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8082")
                )
                .route("batchAPI", r -> r.path("/api/batch/**", "/api/chart/**", "/api/stocks/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://localhost:8086")
                )
                .build();
    }
}
