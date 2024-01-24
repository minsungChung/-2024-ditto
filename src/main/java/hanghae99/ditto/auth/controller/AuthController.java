package hanghae99.ditto.auth.controller;

import hanghae99.ditto.auth.dto.request.AuthenticateCodeRequest;
import hanghae99.ditto.auth.dto.request.SendEmailAuthenticationRequest;
import hanghae99.ditto.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
