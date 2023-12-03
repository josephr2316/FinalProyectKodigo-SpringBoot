package com.lunifer.jo.fpshoppingcart.serviceImplTest;

import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {
    @Mock
    private UserService userService;

    private JwtService jwtService;

    JwtServiceTest() {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService(userService);
    }
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {

        Field secretField = JwtService.class.getDeclaredField("SECRET");
        secretField.setAccessible(true);
        secretField.set(jwtService, "71266gdb127HNJ761238jk98kjasdh12388123712737123asd12737yvfgbhbutvfgybhknjdgxfcvjhknjvgdfhfhgbhjn894okedurirkedjf8r9o4ewsw");

        Field expirationTimeField = JwtService.class.getDeclaredField("EXPIRATION_TIME");
        expirationTimeField.setAccessible(true);
        expirationTimeField.setLong(jwtService, 3600);
    }

    @Test
    void generateToken() {
        User user = new User();
        user.setUsername("testUser");
        user.setRoles(Collections.singletonList("ROLE_USER"));
        when(userService.findByUsername("testUser")).thenReturn(user);

        AuthResponse authResponse = jwtService.generateToken("testUser");
        assertNotNull(authResponse.token());

    }

    @Test
    void validateTokenWithValidToken() {
        User user = new User();
        user.setUsername("testUser");
        user.setRoles(Collections.singletonList("ROLE_USER"));
        when(userService.findByUsername("testUser")).thenReturn(user);

        AuthResponse authResponse = jwtService.generateToken("testUser");

    }


    @Test
    void validateTokenWithExpiredToken() {
        User user = new User();
        user.setUsername("testUser");
        user.setRoles(Collections.singletonList("ROLE_USER"));
        when(userService.findByUsername("testUser")).thenReturn(user);

        AuthResponse authResponse = jwtService.generateToken("testUser");

        // Modify expiration date to create an expired token
        LocalDateTime expiredDateTime = LocalDateTime.now().minusDays(1);
        Date expiredDate = Date.from(expiredDateTime.toInstant(ZoneOffset.ofHours(-4)));


    }

    @Test
    void validateTokenWithMalformedToken() {
        assertThrows(MalformedJwtException.class, () -> jwtService.validateToken("malformedToken", null));
    }


}
