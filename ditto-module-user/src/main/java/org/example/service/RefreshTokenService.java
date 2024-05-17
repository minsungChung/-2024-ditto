package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.MemberRepository;
import org.example.domain.RefreshToken;
import org.example.domain.RefreshTokenRepository;
import org.example.global.exception.NoSuchMemberException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public void saveRefreshToken(String email, String refreshToken){
        Long memberId = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new).getId();
        refreshTokenRepository.save(new RefreshToken(memberId, refreshToken));
    }

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    public void deleteRefreshToken(String refreshToken){
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
        refreshTokenRepository.delete(token);
    }
}
