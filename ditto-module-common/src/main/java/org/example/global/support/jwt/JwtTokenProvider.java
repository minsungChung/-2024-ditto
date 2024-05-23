package org.example.global.support.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.example.global.support.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey = "secretKey";
    private final Long validityInSeconds = 500L;
    private final Long refreshTokenValidityInSeconds = 6000L;
//    private final RedisUtil redisUtil;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email){
        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInSeconds*1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String email){
        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInSeconds*1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("RefreshToken")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getMemberEmail(String token){
        return parseClaims(token).get("email", String.class);
    }

    public Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token){
        try{
//            if (redisUtil.hasKeyBlackList(token)){
//                log.info("dfwef");
//                return false;
//            }

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            log.info(String.valueOf(claims.getBody().getExpiration()));
            return !claims.getBody().getExpiration().before(new Date());

        }catch (JwtException | IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
    }
}
