package org.example.flashcreditspring.util;



import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String secretKey = "Wn5k3sRz87eM13N6M5+12X+5p9xY2zp9VzF3b0QkJqY=";


//    private final Key secretKey;
//
//    public JwtUtil(@Value("${jwt.secret}") String secret) {
//        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
//    }

    public String generateToken(String phoneNum) {
        return Jwts.builder()
                .setSubject(phoneNum)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractPhoneNum(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String phoneNum) {
        return phoneNum.equals(extractPhoneNum(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

}
