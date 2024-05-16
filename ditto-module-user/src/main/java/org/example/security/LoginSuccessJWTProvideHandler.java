package org.example.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.global.support.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = extractEmail(authentication);
        String accessToken = jwtTokenProvider.createToken(email);

        log.info("로그인 성공 email: " + email);

        // JWT 토큰을 쿠키에 저장
        Cookie cookie = new Cookie("Authorization", accessToken);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 메인 페이지로 리다이렉트
        response.sendRedirect("http://localhost:8083/members");

    }

    private String extractEmail(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
