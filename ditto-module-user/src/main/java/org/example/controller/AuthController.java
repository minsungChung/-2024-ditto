package org.example.controller;

import org.example.dto.request.AuthenticateCodeRequest;
import org.example.dto.request.LoginRequest;
import org.example.dto.request.LogoutRequest;
import org.example.dto.request.SendEmailAuthenticationRequest;
import org.example.dto.response.EmailAuthenticationResponse;
import org.example.dto.response.LoginResponse;
import org.example.service.AuthService;
import org.example.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email-authentication")
    public BaseResponse<EmailAuthenticationResponse> sendEmailAuthentication(@Valid @RequestBody SendEmailAuthenticationRequest sendEmailAuthenticationRequest){
        return new BaseResponse<>(authService.sendEmailAuthentication(sendEmailAuthenticationRequest));
    }

    @PostMapping("/authentication-code")
    public BaseResponse<EmailAuthenticationResponse> authenticateCode(@Valid @RequestBody AuthenticateCodeRequest authenticateCodeRequest){
        return new BaseResponse<>(authService.authenticateCode(authenticateCodeRequest));
    }
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return new BaseResponse<>(authService.login(loginRequest));
    }

//    @DeleteMapping("/logout")
//    public BaseResponse<String> logout(@Valid @RequestBody LogoutRequest logoutRequest){
//        return new BaseResponse<>(authService.logout(logoutRequest));
//    }
}
