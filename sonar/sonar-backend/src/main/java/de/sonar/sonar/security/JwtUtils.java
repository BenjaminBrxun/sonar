package de.sonar.sonar.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {


    // Mindestens 256 Bit für HS256 (32 Byte)
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "dein-geheimer-schluessel-der-lang-genug-ist-um-sicher-zu-sein!".getBytes()
    );

    private static final long EXPIRATION_TIME = 8600000L; // z.B. ~2.4 Stunden

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    public String extractEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        String email = extractEmailFromToken(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    // 🔐 Mindestens 256-bit Key (32 Byte)
//    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
//            "ein-sehr-sicherer-und-langer-jwt-signatur-schluessel!".getBytes()
//    );
//
//    // ⏳ Gültigkeit: 2h
//    private static final long EXPIRATION_MILLIS = 1000 * 60 * 60 * 2;
//
//    public String generateToken(String email) {
//        Instant now = Instant.now();
//        return Jwts.builder()
//                .issuer("sonar-app") // optional
//                .subject(email)
//                .issuedAt(Date.from(now))
//                .expiration(Date.from(now.plusMillis(EXPIRATION_MILLIS)))
//                .signWith(SECRET_KEY, Jwts.SIG.HS256)
//                .compact();
//    }
//
//    public String extractEmail(String token) {
//        return Jwts.parser()
//                .verifyWith(SECRET_KEY)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }
//
//    public boolean isTokenValid(String token, String email) {
//        try {
//            Claims claims = Jwts.parser()
//                    .verifyWith(SECRET_KEY)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//
//            return claims.getSubject().equals(email)
//                    && !claims.getExpiration().before(new Date());
//
//        } catch (JwtException e) {
//            return false;
//        }
//    }
}
