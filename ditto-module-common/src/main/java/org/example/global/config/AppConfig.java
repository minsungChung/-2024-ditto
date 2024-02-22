package org.example.global.config;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@AllArgsConstructor
public class AppConfig {
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }
}
