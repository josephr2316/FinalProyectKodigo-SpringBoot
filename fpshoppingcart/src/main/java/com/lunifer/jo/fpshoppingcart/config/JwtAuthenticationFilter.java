package com.lunifer.jo.fpshoppingcart.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunifer.jo.fpshoppingcart.dto.LoginRequestDTO;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        System.out.println("🔐 JwtAuthenticationFilter.attemptAuthentication called");
        
        String username = "";
        String password = "";
        
        try {
            // Try first with JSON
            if (isJsonRequest(request)) {
                try {
                    LoginRequestDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
                    username = loginRequest.getUsername();
                    password = loginRequest.getPassword();
                                         System.out.println("📝 Processing JSON - Username: " + username);
                } catch (IOException e) {
                    // If JSON fails, try with form-data
                    username = request.getParameter("username");
                    password = request.getParameter("password");
                                         System.out.println("📝 Fallback to form-data - Username: " + username);
                }
            } else {
                // If not JSON, use form-data
                username = request.getParameter("username");
                password = request.getParameter("password");
                                 System.out.println("📝 Processing form-data - Username: " + username);
            }
            
            // Validate that fields are not empty
            if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
                System.out.println("❌ Username or password empty");
                throw new RuntimeException("Username and password are required");
            }
            
                         System.out.println("✅ Credentials processed successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Error procesando credenciales: " + e.getMessage());
            throw new RuntimeException("Error processing credentials: " + e.getMessage());
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

                        System.out.println("🔑 Creating authentication token for: " + username);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.contains("application/json");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

                    System.out.println("🎉 Authentication successful for: " + authResult.getName());
        
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        AuthResponse token = jwtService.generateToken(user.getUsername());

        System.out.println("🎫 JWT Token generated: " + token);

        // Configure response headers
        response.addHeader("Authorization", "Bearer " + token.token());
        response.addHeader("Content-Type", "application/json");

        // Create JSON response
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token.token());
        httpResponse.put("message", "Authentication Successful");
        httpResponse.put("username", user.getUsername());
        httpResponse.put("authorities", user.getAuthorities().stream().map(Object::toString).toList());

        // Write response
        String jsonResponse = new ObjectMapper().writeValueAsString(httpResponse);
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        System.out.println("✅ Response sent successfully");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException failed) throws IOException, ServletException {
        
        System.out.println("❌ Authentication failed: " + failed.getMessage());
        
        // Create error response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Authentication failed");
        errorResponse.put("message", failed.getMessage());
        errorResponse.put("timestamp", System.currentTimeMillis());

        // Configure response
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Write error response
        String jsonError = new ObjectMapper().writeValueAsString(errorResponse);
        response.getWriter().write(jsonError);
        response.getWriter().flush();
        
        System.out.println("❌ Error response sent");
    }
}
