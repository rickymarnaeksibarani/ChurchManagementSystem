package ChurchManagementSystem.CMS.core.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (SECRET != null && !SECRET.isEmpty()) {
            // kalau kamu masih ingin pakai SECRET dari properties
            byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
            if (keyBytes.length < 64) {
                throw new IllegalArgumentException("Secret key must be at least 64 bytes for HS512");
            }
            key = Keys.hmacShaKeyFor(keyBytes);
        } else {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            System.out.println("Generated secure random JWT key: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        }
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
