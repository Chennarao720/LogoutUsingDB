package com.example.productinventory.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long expiration = 1000*60*30; // 30 min

    private final long refreshToken=7*24*60*60*1000;// 7days


    public JwtService() {
        this.secretKey = Jwts.SIG.HS256.key().build();
    }

    //Generation of JWT token(After successfull authentication this will be generaeted)
    public String generateToken(
            UserDetails userDetails) {

        Date now = new Date();

        Date expiry =
                new Date(
                        now.getTime() + expiration
                );

        return Jwts.builder()
                .subject(
                        userDetails.getUsername()
                )
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }
// for refresh token

    public String generateRefreshToken(UserDetails details){
        Date date=new Date();
        Date expiry =new Date(date.getTime()+refreshToken);

        return Jwts.builder().subject(details.getUsername())
                .issuedAt(date)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();

    }

    //Authorization(extracting token from jwt and valiadting token)
    public String extractUsername(
            String token) {

        return parseToken(token)
                .getPayload()
                .getSubject();
    }


    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        )
                &&
                !isTokenExpired(token);
    }

// for login Api purpose
    private boolean isTokenExpired(
            String token) {

        Date expirationDate =
                parseToken(token)
                        .getPayload()
                        .getExpiration();

        return expirationDate.before(
                new Date()
        );
    }


    private Jws<Claims> parseToken(
            String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
    }

    //for logout
    public Instant extractExpiration(String token) {
        return parseToken(token)
                .getPayload()
                .getExpiration()
                .toInstant();
    }
}

