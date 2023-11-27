package com.lunifer.jo.fpshoppingcart.controller;

import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthRequest;
import com.lunifer.jo.fpshoppingcart.security.auth.AuthResponse;
import com.lunifer.jo.fpshoppingcart.service.impl.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class AuthenticationController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger =  LoggerFactory.getLogger(AuthenticationController.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String index(){
        return "Welcome to our Shopping Cart Application";
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> auth(@RequestParam("username") String username, @RequestParam("password") String password){

        User user = userRepository.findByUsername(username);
        if(user==null || !user.getPassword().equals(passwordEncoder.encode(password))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        AuthResponse token = jwtService.generateToken(username);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/generateToken")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        logger.info("Request Token Generation: "+authRequest.toString());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            throw new UsernameNotFoundException("Invalid user...");
        }
    }
}
