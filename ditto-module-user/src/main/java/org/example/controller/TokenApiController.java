package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token", description = "Token API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 사용해 Access Token을 재발급 받습니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public String createNewAccessToken(@RequestHeader("Authorization") String refreshToken){
        return tokenService.createNewAccessToken(refreshToken);
    }
}
