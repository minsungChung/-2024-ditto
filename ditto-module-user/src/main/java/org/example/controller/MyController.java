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

        // 쿠키 삭제
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // 로그인 페이지로 리다이렉트
        return "redirect:http://localhost:8083/login-page";
    }
}
