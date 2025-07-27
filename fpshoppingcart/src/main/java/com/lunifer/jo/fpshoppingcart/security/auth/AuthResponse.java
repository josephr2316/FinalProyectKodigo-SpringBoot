package com.lunifer.jo.fpshoppingcart.security.auth;

public record AuthResponse(String token, long expiresAt ) {
}
