package com.lunifer.jo.fpshoppingcart.config;

import com.lunifer.jo.fpshoppingcart.service.UserService;
import com.lunifer.jo.fpshoppingcart.security.CustomUserDetailsService;
import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final UserServiceImpl userService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    
    @Autowired
    public JwtAuthorizationFilter(JwtService jwtService, UserServiceImpl userService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
       // final String authorizationHeader = request.getHeader("Authorization");
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwtToken);
            } catch (Exception e) {
                // Handle token extraction/validation errors
                System.out.println("Error extracting username from token: " + e.getMessage());
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String roles = jwtService.extractClaim(jwtToken,"roles");
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            for (String role : roles.split(",")){
                logger.info("Filter Role : " + role);
                grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_"+role));
            }
            logger.info("JWT USER : " + jwtService.extractUsername(jwtToken));

            // I can fetch roles from the database, but in a JWT schema, the aim is to be decentralized.
             UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            //UserDetails userDetails = new User(jwtService.extractUsername(jwtToken),"null",true, true, true, true, grantedAuthorityList);

            if (jwtService.validateToken(jwtToken, userDetails) || jwtService.validateToken(jwtToken)) {

                //In case we seek the roles from the database. "
                //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        //userDetails, null, userDetails.getAuthorities());

                //Switching directly
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorityList);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}