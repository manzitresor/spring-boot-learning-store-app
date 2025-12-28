package com.codewithmosh.store.services;


import com.codewithmosh.store.config.JwtConfig;
import com.codewithmosh.store.entities.Role;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {// 15min
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private String generateToken(User user, long expiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .claim("role", user.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * expiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }

    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

    public Role getRoleFromToken(String token) {
        return Role.valueOf(getClaims(token).get("role", String.class));
    }

}
