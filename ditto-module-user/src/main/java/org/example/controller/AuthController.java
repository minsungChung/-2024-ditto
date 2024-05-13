package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.AuthenticateCodeRequest;
import org.example.dto.request.SendEmailAuthenticationRequest;
import org.example.dto.response.EmailAuthenticationResponse;
import org.example.service.AuthService;
import org.example.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

//    @PostMapping("/login")
//    public BaseResponse<LoginResponse> login(LoginRequest loginRequest){
//        log.info(loginRequest.getEmail() + loginRequest.getPassword());
//        return new BaseResponse<>(authService.login(loginRequest));
//    }

//    @DeleteMapping("/logout")
//    public BaseResponse<String> logout(@Valid @RequestBody LogoutRequest logoutRequest){
//        return new BaseResponse<>(authService.logout(logoutRequest));
//    }
}
