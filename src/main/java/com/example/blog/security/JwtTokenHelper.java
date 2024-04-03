package com.example.blog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
    public static final long JWT_TOKEN_VALIDITY =
            5 * 60 * 60;

    public String secretKey = "jwtTokenKey";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationdateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromTokens(token);
        return claimResolver.apply(claims);
    }

    public Claims getAllClaimsFromTokens(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload();
    }

    public Boolean isTokenExpired(String token) {
        final Date exxpiry = getExpirationdateFromToken(token);
        return exxpiry.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username =
                getUsernameFromToken(userDetails.getUsername());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        return doGenerateToken(claims, userDetails.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String username) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(secret).compact();

    }

}
