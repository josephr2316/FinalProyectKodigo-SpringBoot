package com.lunifer.jo.fpshoppingcart.config;

import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import com.lunifer.jo.fpshoppingcart.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public SecurityConfig(@Lazy JwtAuthorizationFilter jwtAuthorizationFilter, CustomUserDetailsService userDetailsService, JwtService jwtService) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChainGeneral(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        
        // Configurar userDetailsService
        http.userDetailsService(userDetailsService);
        
        // Configurar filtros en el orden correcto
        http.addFilter(jwtAuthenticationFilter);
        http.addFilterAfter(jwtAuthorizationFilter, JwtAuthenticationFilter.class);
        
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( authorization ->{
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/test/public")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/test/generate-hash")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/test/list-users")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/test/create-admin-test")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/test/update-all-passwords")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/users")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/api-docs/**")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**")).permitAll();
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/categories")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/categories/**")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/categories")).hasAuthority("ROLE_ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/categories/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/categories/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/categories/disable/**")).hasRole("ADMIN");
                    // Remove duplicate and incorrect invoice configurations

                    // Orders endpoints
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders/{id}")).hasAnyRole("ADMIN","USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders/user")).hasAnyRole("ADMIN","USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/orders/user/**")).hasAnyRole("ADMIN","USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/orders")).hasAnyRole("ADMIN","USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/orders/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/orders/**")).hasRole("ADMIN");
                    
                    // Products endpoints
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products")).hasAnyRole("ADMIN","USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products/**")).hasAnyRole("ADMIN","USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/products")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/products/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/products/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products/byName/**")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/products/search")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/products/disable/**")).hasRole("ADMIN");
                    
                    // Reviews endpoints
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/reviews")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/reviews/{id}")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/reviews")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/reviews/**")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/reviews/product/**")).hasAnyRole("ADMIN", "USER");
                    
                    // Users endpoints
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/users")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/users/{id}")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/users/me")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/users/**")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/users/**")).hasRole("ADMIN");
                    
                    // Invoices endpoints (ADMIN ONLY - invoices are administrative documents)
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/invoices")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/invoices/{id}")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/invoices")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/invoices/**")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/invoices/orders")).hasRole("ADMIN");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher("/api/invoices/orders/")).hasRole("ADMIN");
                    
                    // Cart endpoints
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/api/cart/{userId}")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/api/cart/{userId}/add")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT,"/api/cart/{userId}/item/{cartItemId}")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/cart/{userId}/item/{cartItemId}")).hasAnyRole("ADMIN", "USER");
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE,"/api/cart/{userId}/clear")).hasAnyRole("ADMIN", "USER");
                    
                    // Authentication endpoints
                    authorization.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/user-details")).hasAnyRole("ADMIN", "USER");


                    authorization.anyRequest().authenticated();
                })
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
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
