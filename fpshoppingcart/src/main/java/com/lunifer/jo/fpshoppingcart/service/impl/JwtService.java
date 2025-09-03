package com.lunifer.jo.fpshoppingcart.service.impl;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
    
    private final UserService userService;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(@Lazy UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
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

    public String extractClaim(String token, String name) {
        Claims claims = extractAllClaims(token);
        Object claimValue = claims.get(name);

        if (claimValue instanceof ArrayList) {
            return claimValue.toString();
        }
        return claims.get(name, String.class);
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public AuthResponse generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            claims.put("roles", user.getRoles().stream()
                    .map(Enum::name)
                    .reduce("", (a, b) -> a.isEmpty() ? b : a + "," + b));
        }

        return createToken(claims, username);
    }

    private AuthResponse createToken(Map<String, Object> claims, String username) {
        logger.info("Generating JWT for the user: " + username);
        
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
        logger.info("The current date: " + localDateTime);
        
        Date expirationDate = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4)));
        
        String jwt = Jwts.builder()
                .claims().add(claims).and()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
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

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
    