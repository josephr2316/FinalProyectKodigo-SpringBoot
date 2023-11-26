package com.lunifer.jo.fpshoppingcart.service.impl;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${token.jwt}")
    private String SECRET;
    @Value("${token.time}")
    private long EXPIRATION_TIME;
    private UserService userService;

    private final Logger logger =  LoggerFactory.getLogger(JwtService.class);

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractClaim(String token, String name){
        Claims claims = extractAllClaims(token);
        return claims.get(name, String.class);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

        return Jwts.
                parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public AuthResponse generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        //Getting the user's roles
        User user = userService.findByUsername(username);
        claims.put("roles", String.join(",", "user.Role"));
        //
        return createToken(claims, username);
    }
    private AuthResponse createToken(Map<String, Object> claims, String username) {
        logger.info("Generating JWT for the user: "+username);
        //Generating the valid date
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
        logger.info("The current date: "+localDateTime);
        //
        Date expirationDate = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4)));
        //Generating the GWT
        String jwt =  Jwts.builder()
                .issuer("PWA-JWT")
                .claims().empty().add(claims).and()
                .subject(username)
                .expiration(expirationDate)
                .signWith(getSignKey())
                .compact();

        return new AuthResponse(jwt, expirationDate.getTime());
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
