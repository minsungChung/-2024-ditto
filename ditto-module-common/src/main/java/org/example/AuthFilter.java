package org.example;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

        if (routerValidator.isSecured.test(request)){

            if (this.isAuthMissing(request)) {
                throw new IllegalArgumentException();
            }

            String token = this.getAuthHeader(request);
            token = token.substring(7);

            log.info(token);

            if (!jwtTokenProvider.validateToken(token)) {
                throw new IllegalArgumentException();
            }

            this.updateRequest(exchange, token);
        }
        return chain.filter(exchange);

    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtTokenProvider.parseClaims(token);
        exchange.getRequest().mutate()
                .header("memberId", String.valueOf(claims.get("memberId")))
                .build();
    }
}
