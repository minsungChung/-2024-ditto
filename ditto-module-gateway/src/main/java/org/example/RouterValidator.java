package org.example;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/members", "/api/auth/email-authentication", "/api/auth/authentication-code", "/api/auth/login",
            "/api/follow/{toMemberId}/followings", "/api/follow/{toMemberId}/followers",
            "/api/follow/{toMemberId}/followings", "/api/follow/{toMemberId}/followers"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
