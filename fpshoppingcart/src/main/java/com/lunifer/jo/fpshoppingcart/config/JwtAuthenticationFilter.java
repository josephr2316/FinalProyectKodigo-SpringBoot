package com.lunifer.jo.fpshoppingcart.config;

import com.lunifer.jo.fpshoppingcart.repository.UserService;
import com.lunifer.jo.fpshoppingcart.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private UserService userDetailsService;

    private  JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
              //  username = jwtService..extractUsername(jwtToken);
            } catch (Exception e) {
                // Handle token extraction/validation errors
                System.out.println("Error extracting username from token: " + e.getMessage());
            }
        }

    }
}
