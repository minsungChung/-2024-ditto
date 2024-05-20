package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.service.MyService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;

    @GetMapping("/members")
    public String list(Model model){

        List<Map<String, Object>> members = myService.findMembers();

        model.addAttribute("memberList", members);

        return "html/member/list.html";
    }

    @GetMapping("/login-page")
    public String loginPage(){
        return "html/member/login.html";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "html/member/signup.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 사용자 로그아웃 처리
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        // HttpServletRequest에서 RefreshToken 쿠키 가져오기
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("RefreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }


        // 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("Authorization", null);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("RefreshToken", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        // 로그인 페이지로 리다이렉트
        return "redirect:http://localhost:8083/login-page";
    }
}
