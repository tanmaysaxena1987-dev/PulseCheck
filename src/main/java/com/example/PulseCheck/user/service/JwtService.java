package com.example.PulseCheck.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private String key="";
    public JwtService(){
        try{
            KeyGenerator keyGen=KeyGenerator.getInstance("HMACSHA256");
            SecretKey secretKey=keyGen.generateKey();
            key= Base64.getEncoder().encodeToString(secretKey.getEncoded());
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> Claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(Claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*30))
                .and()
                .signWith(getKey())
                .compact();
    }
    public SecretKey getKey(){
        byte[] keyBytes = Base64.getDecoder().decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
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
                .verifyWith(getKey())
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
