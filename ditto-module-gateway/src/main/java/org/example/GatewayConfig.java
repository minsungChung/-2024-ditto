package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator ms1Route(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userAPI", r -> r.path("/**", "/api/members/**", "/api/auth/**", "/api/mypage/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://user:8087")
                )
                .route("activityAPI", r -> r.path("/api/posts/**", "/api/follow/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://activity:8081")
                )
                .route("newsfeedAPI", r -> r.path("/api/newsfeed/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("http://newsfeed:8082")
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
