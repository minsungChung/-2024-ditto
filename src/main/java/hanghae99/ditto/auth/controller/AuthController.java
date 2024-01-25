package hanghae99.ditto.auth.controller;

import hanghae99.ditto.auth.dto.request.AuthenticateCodeRequest;
import hanghae99.ditto.auth.dto.request.LoginRequest;
import hanghae99.ditto.auth.dto.request.LogoutRequest;
import hanghae99.ditto.auth.dto.request.SendEmailAuthenticationRequest;
import hanghae99.ditto.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email-authentication")
    public HttpEntity<?> sendEmailAuthentication(@RequestBody SendEmailAuthenticationRequest sendEmailAuthenticationRequest){
        return authService.sendEmailAuthentication(sendEmailAuthenticationRequest);
    }

    @PostMapping("/authentication-code")
    public HttpEntity<?> authenticateCode(@RequestBody AuthenticateCodeRequest authenticateCodeRequest){
        return authService.authenticateCode(authenticateCodeRequest);
    }
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @DeleteMapping("/logout")
    public HttpEntity<?> logout(@RequestBody LogoutRequest logoutRequest){
        return authService.logout(logoutRequest);
    }
}
