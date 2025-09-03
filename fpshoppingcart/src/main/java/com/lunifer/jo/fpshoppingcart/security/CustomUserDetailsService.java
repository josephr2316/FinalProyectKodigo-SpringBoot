package com.lunifer.jo.fpshoppingcart.security;

import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("🔍 CustomUserDetailsService.loadUserByUsername called with: " + usernameOrEmail);
        
        try {
            // Search by username OR email
            User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + usernameOrEmail));

            System.out.println("✅ User found: " + user.getUsername() + ", active: " + user.isActive());

            // Verify that user is active
            if (!user.isActive()) {
                throw new UsernameNotFoundException("Inactive user: " + usernameOrEmail);
            }

            // Convertir roles a SimpleGrantedAuthority
            var authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toList());

            System.out.println("✅ Roles found: " + authorities);

            // Create Spring Security UserDetails
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(!user.isActive())
                    .build();
        } catch (Exception e) {
            System.out.println("❌ Error in CustomUserDetailsService: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
