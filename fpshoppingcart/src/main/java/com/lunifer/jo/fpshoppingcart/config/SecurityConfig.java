package com.lunifer.jo.fpshoppingcart.config;

import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import com.lunifer.jo.fpshoppingcart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final UserServiceImpl userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, UserServiceImpl userDetailsService, JwtService jwtService) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChainGeneral(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorization ->{
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/categories")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/categories/**")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/categories")).hasAuthority("ROLE_ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/categories/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/categories/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/categories/disable/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"api/invoices/**")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"api/invoices/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"api/invoices/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"api/invoices")).hasAnyRole("ADMIN", "BUYER");

                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders/user")).hasAnyRole("ADMIN","BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders/user/**")).hasAnyRole("ADMIN","BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/orders")).hasAnyRole("ADMIN","BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/orders/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/orders/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products")).hasAnyRole("ADMIN","BUYER" );
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products/**")).hasAnyRole("ADMIN","BUYER" );
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/products/Create")).hasRole("ADMIN" );
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/products/**")).hasRole("ADMIN" );
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/products/**")).hasRole("ADMIN" );
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/products/{disable}/**")).hasRole("ADMIN" );
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products/byName/**")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products/search")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"api/reviews")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"api/reviews/**")).hasAnyRole("ADMIN", "BUYER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"api/reviews/product/**")).hasAnyRole("ADMIN", "BUYER");

















                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/products")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/products/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/products/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/products/disable/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/invoices/orders")).hasAnyRole("BUYER","ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/api/invoices/orders/")).hasRole("ADMIN");


                    authorization.anyRequest().authenticated();
                })
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
