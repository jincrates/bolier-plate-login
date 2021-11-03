package me.jincrates.login.security;

import me.jincrates.login.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //https://github.com/jwtk/jjwt 참고
    public String create(UserEntity userEntity) {
        //String jws = Jwts.builder().setSubject(userEntity.getId()).signWith(key).compact();

        // 기한은 지금부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        // JWT Token 생성
        String jwtToken = Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // payload에 들어갈 내용
                .setSubject(userEntity.getId())  // sub: 토큰 제목
                .setIssuer("login app")  // iss: 토큰 발급자
                .setIssuedAt(new Date())  //iat: 토큰 발급된 시간
                .setExpiration(expiryDate)  // exp: 토큰 만료시간
                .signWith(SECRET_KEY)
                .compact();

        return jwtToken;
    }

    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그중 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
