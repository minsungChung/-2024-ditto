package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.AuthenticateCodeRequest;
import org.example.dto.request.SendEmailAuthenticationRequest;
import org.example.dto.response.EmailAuthenticationResponse;
import org.example.service.AuthService;
import org.example.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email-authentication")
    @Operation(summary = "이메일 인증코드 보내기", description = "사용자의 이메일을 검증합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<EmailAuthenticationResponse> sendEmailAuthentication(@Valid @RequestBody SendEmailAuthenticationRequest sendEmailAuthenticationRequest){
        return new BaseResponse<>(authService.sendEmailAuthentication(sendEmailAuthenticationRequest));
    }

    @PostMapping("/authentication-code")
    @Operation(summary = "인증코드 검증", description = "입력한 코드를 검증합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    public BaseResponse<EmailAuthenticationResponse> authenticateCode(@Valid @RequestBody AuthenticateCodeRequest authenticateCodeRequest){
        return new BaseResponse<>(authService.authenticateCode(authenticateCodeRequest));
    }
}
