package hanghae99.ditto.auth.controller;

import hanghae99.ditto.auth.dto.request.AuthenticateCodeRequest;
import hanghae99.ditto.auth.dto.request.LoginRequest;
import hanghae99.ditto.auth.dto.request.LogoutRequest;
import hanghae99.ditto.auth.dto.request.SendEmailAuthenticationRequest;
import hanghae99.ditto.auth.dto.response.EmailAuthenticationResponse;
import hanghae99.ditto.auth.dto.response.LoginResponse;
import hanghae99.ditto.auth.service.AuthService;
import hanghae99.ditto.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email-authentication")
    public BaseResponse<EmailAuthenticationResponse> sendEmailAuthentication(@RequestBody SendEmailAuthenticationRequest sendEmailAuthenticationRequest){
        return new BaseResponse(authService.sendEmailAuthentication(sendEmailAuthenticationRequest));
    }

    @PostMapping("/authentication-code")
    public BaseResponse<EmailAuthenticationResponse> authenticateCode(@RequestBody AuthenticateCodeRequest authenticateCodeRequest){
        return new BaseResponse(authService.authenticateCode(authenticateCodeRequest));
    }
    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return new BaseResponse(authService.login(loginRequest));
    }

    @DeleteMapping("/logout")
    public BaseResponse<String> logout(@RequestBody LogoutRequest logoutRequest){
        return new BaseResponse(authService.logout(logoutRequest));
    }
}
