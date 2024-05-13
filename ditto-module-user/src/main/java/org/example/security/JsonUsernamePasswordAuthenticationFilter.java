package org.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
@NoArgsConstructor
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String CONTENT_TYPE = "application/json";
    private static final String USERNAME_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("들어오긴하니?");
        if (request.getContentType() == null || request.getContentType().equals(CONTENT_TYPE)){
            throw new AuthenticationServiceException("Authentication Content-Type not supported");
        }

        String username = request.getParameter("email");
        String password = request.getParameter("password");

        log.info(username);
        log.info(password);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
