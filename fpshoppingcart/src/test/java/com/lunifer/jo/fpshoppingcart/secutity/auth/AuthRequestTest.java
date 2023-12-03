package com.lunifer.jo.fpshoppingcart.secutity.auth;

import com.lunifer.jo.fpshoppingcart.security.auth.AuthRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthRequestTest {
    @Test
    void testRecordConstructor() {
        // Arrange
        String username = "juan.perez";
        String password = "password123";

        // Act
        AuthRequest authRequest = new AuthRequest(username, password);

        // Assert
        assertNotNull(authRequest);
        assertEquals(username, authRequest.username());
        assertEquals(password, authRequest.password());
    }

    @Test
    void testToString() {
        // Arrange
        String username = "juan.perez";
        String password = "password123";

        // Act
        AuthRequest authRequest = new AuthRequest(username, password);

        // Assert
        assertEquals("AuthRequest[username=juan.perez, password=password123]", authRequest.toString());
    }
}
