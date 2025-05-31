//package com.devcolibri.backend.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.time.Instant;
//import java.util.Date;
//
//
//@Service
//public class JwtService {
//
//    private static final String SECRET_KEY = "twojSuperTajnyKluczJWT"; // Zmien na mocny sekret
//
//    private Key key;
//
//    @PostConstruct
//    public void init() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateToken(String subject) {
//        Instant now = Instant.now();
//        return Jwts.builder()
//                .subject(subject)
//                .issuedAt(Date.from(now))
//                .expiration(Date.from(now.plusSeconds(3600))) // 1 godzina
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        Claims claims = Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//
//        return claims.getSubject();
//    }
//
//
//}
//
