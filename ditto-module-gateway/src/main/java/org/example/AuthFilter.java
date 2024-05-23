package org.example;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.IllformedLocaleException;


@Slf4j
@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RouterValidator routerValidator;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        log.info(request.getURI().getRawPath());
        log.info(String.valueOf(routerValidator.isSecured.test(request)));

        if (routerValidator.isSecured.test(request)) {
            String accessToken = this.getAuthTokenFromCookie(request, "Authorization");
            String refreshToken = this.getAuthTokenFromCookie(request, "RefreshToken");

            log.info("Access Token: {}", accessToken);
            log.info("Refresh Token: {}", refreshToken);

            if (accessToken == null && refreshToken == null) {
                throw new IllegalArgumentException("Authorization cookie is missing");
            }

            if (accessToken != null){
                if (jwtTokenProvider.validateToken(accessToken)){
                    this.updateRequest(exchange, accessToken);
                } else {
                    throw new IllformedLocaleException("Invalid Token");
                }
            } else {
                if (!jwtTokenProvider.validateToken(refreshToken)){
                    throw new IllegalArgumentException("Both Access and Refresh token are invalid");
                }
                else {
                    // Refresh Token을 사용하여 새로운 Access Token을 발급받는 로직
                    return webClientBuilder.build()
                            .post()
                            .uri("http://localhost:8083/api/token")
                            .header("Authorization", refreshToken)
                            .retrieve()
                            .bodyToMono(String.class)
                            .flatMap(newAccessToken -> {
                                this.updateRequestWithNewToken(exchange, newAccessToken);
                                return chain.filter(exchange);
                            });
                }
            }
        }
        return chain.filter(exchange);
    }

    private String getAuthTokenFromCookie(ServerHttpRequest request, String cookieName) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (cookies != null && cookies.containsKey(cookieName)) {
            return cookies.getFirst(cookieName).getValue();
        }
        return null;
    }

    private void updateRequest(ServerWebExchange exchange, String token) {
        Claims claims = jwtTokenProvider.parseClaims(token);
        exchange.getRequest().mutate()
                .header("memberEmail", String.valueOf(claims.get("email")))
                .build();
    }

    private void updateRequestWithNewToken(ServerWebExchange exchange, String newAccessToken) {
        exchange.getResponse().addCookie(ResponseCookie.from("Authorization", newAccessToken)
                .path("/")
                .maxAge(jwtTokenProvider.getValidityInMilliseconds()/1000)
                .build());

        this.updateRequest(exchange, newAccessToken);
    }
}

