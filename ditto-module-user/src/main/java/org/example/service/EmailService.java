package org.example.service;

import org.example.dto.request.SendEmailAuthenticationRequest;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public Boolean sendEmailAuthentication(SendEmailAuthenticationRequest sendEmailAuthenticationRequest,
                                           String authenticationCode){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            // 이메일 제목 설정
            message.setSubject("사이트 회원가입 인증번호 입니다.");

            // 이메일 수신자 설정
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(sendEmailAuthenticationRequest.getEmail(), "", "UTF-8")));

            // 이메일 내용 설정
            message.setText(setContext(authenticationCode), "UTF-8", "html");

            // 송신
            javaMailSender.send(message);
        } catch (MessagingException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        return true;
    }

    private String setContext(String authenticationCode){
        Context context = new Context();
        context.setVariable("authenticationCode", authenticationCode);
        return templateEngine.process("email-authentication", context);
    }
}
