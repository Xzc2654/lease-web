package com.xzc.lease.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtil {
    private static final SecretKey key = Keys.hmacShaKeyFor("EqhEqw2vQEDrLeFL5LiT3VVl7DIeIHHM".getBytes());

    public static String createToken(Long id, String username) {

        String jwt = Jwts.builder().
                setExpiration(new Date(System.currentTimeMillis() + 3600000)).
                setSubject("LOGIN_USER").
                claim("userId", id).
                claim("username", username).
                signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }

//    public static void main(String[] args) {
//        System.out.println(createToken(1L, "usernamee"));
//    }
}
