package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.global.support.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public String createNewAccessToken(String refreshToken){
        if(!jwtTokenProvider.validateToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }

        return jwtTokenProvider.createToken(jwtTokenProvider.getMemberEmail(refreshToken));
    }
}
