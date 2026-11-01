package com.example.PulseCheck.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private static final Long EXPIRATION_MS = 1000L * 60 * 30;
    public JwtService(@Value("${jwt.secret}") String secretKey) {
        this.secretKey=Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey.trim()));
    }
    public String generateToken(UserDetails userDetails) {
        Date date = new Date();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(date)
                .expiration(new Date(date.getTime()+EXPIRATION_MS))
                .signWith(secretKey)
                .compact();
    }
    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> function){
        final Claims claims=extractAllClaims(token);
        return function.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Boolean validateToken(String token,UserDetails userDetails){
        final String Email=extractEmail(token);
        return (Email.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){return extractExpiration(token).before(new Date());}
    public Date extractExpiration(String token){return extractClaim(token, Claims::getExpiration);}
}
