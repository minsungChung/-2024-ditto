package hanghae99.ditto.auth.support.jwt;

import hanghae99.ditto.auth.exception.InvalidTokenException;
import hanghae99.ditto.auth.support.redis.RedisUtil;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey = "secretKey";
    private final Long validityInMilliseconds = 1000L * 60 * 60;
    private final RedisUtil redisUtil;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long memberId){
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Long getMemberId(String token){
        return parseClaims(token).get("memberId", Long.class);
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
            if (redisUtil.hasKeyBlackList(token)){
                return false;
            }
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            claims.getBody().getExpiration().before(new Date());
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new InvalidTokenException();
        }
    }
}
