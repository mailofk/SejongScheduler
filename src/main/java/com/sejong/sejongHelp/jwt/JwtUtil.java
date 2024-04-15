package com.sejong.sejongHelp.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}")String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm() //양벙형 암호화 방식인 HS256 사용
        );
    }

    public String getUserName(String token) {

        return Jwts.parser()
                .verifyWith(secretKey) //가지고 있는 secretKey 이용하여 검증 진행
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("studentId", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String studentId, Long expiredMs) {

        return Jwts.builder()
                .claim("studentId", studentId) //payload 부분에 학번 정보는 기입하도록 설정
                .issuedAt(new Date(System.currentTimeMillis())) //현재의 발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
