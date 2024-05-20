package org.example.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.global.support.jwt.JwtTokenProvider;
import org.example.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = extractEmail(authentication);
        String accessToken = jwtTokenProvider.createToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        refreshTokenService.saveRefreshToken(email, refreshToken);

        log.info("로그인 성공 email: " + email);

        // JWT 토큰을 쿠키에 저장
        Cookie accessTokencookie = new Cookie("Authorization", accessToken);
        accessTokencookie.setPath("/");
        accessTokencookie.setMaxAge(Math.toIntExact(jwtTokenProvider.getValidityInSeconds()));

        // Refresh 토큰을 쿠키에 저장
        Cookie refreshTokencookie = new Cookie("RefreshToken", refreshToken);
        refreshTokencookie.setPath("/");
        refreshTokencookie.setMaxAge(Math.toIntExact(jwtTokenProvider.getRefreshTokenValidityInSeconds()));

        response.addCookie(accessTokencookie);
        response.addCookie(refreshTokencookie);

        // 메인 페이지로 리다이렉트
        response.sendRedirect("http://localhost:8083/members");

    }

    private String extractEmail(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
