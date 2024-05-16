package org.example;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RouterValidator routerValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        log.info(request.getURI().getRawPath());
        log.info(String.valueOf(routerValidator.isSecured.test(request)));

        if (routerValidator.isSecured.test(request)) {
            String token = this.getAuthTokenFromCookie(request);

            log.info(token);

            if (token == null) {
                throw new IllegalArgumentException("Authorization cookie is missing");
            }

            if (!jwtTokenProvider.validateToken(token)) {
                throw new IllegalArgumentException("Invalid JWT token");
            }

            this.updateRequest(exchange, token);
        }
        return chain.filter(exchange);
    }

    private String getAuthTokenFromCookie(ServerHttpRequest request) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (cookies != null && cookies.containsKey("Authorization")) {
            return cookies.getFirst("Authorization").getValue();
        }
        return null;
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtTokenProvider.parseClaims(token);
        exchange.getRequest().mutate()
                .header("memberId", String.valueOf(claims.get("email")))
                .build();
    }
}

