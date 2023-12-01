package com.lunifer.jo.fpshoppingcart.secutity.auth;

import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthResponseTest {
    @Test
    void testRecordConstructor() {
        // Arrange
        String token = "someToken";
        long expiresIn = 3600;

        // Act
        AuthResponse authResponse = new AuthResponse(token, expiresIn);

        // Assert
        assertNotNull(authResponse);
        assertEquals(token, authResponse.token());
        assertEquals(expiresIn, authResponse.expiresIn());
    }

    @Test
    void testToString() {
        // Arrange
        String token = "someToken";
        long expiresIn = 3600;

        // Act
        AuthResponse authResponse = new AuthResponse(token, expiresIn);

        // Assert
        assertEquals("AuthResponse[token=someToken, expiresIn=3600]", authResponse.toString());
    }
}
