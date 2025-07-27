package com.lunifer.jo.fpshoppingcart.security;

import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Mapea tu User a un objeto UserDetails de Spring
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRoles().stream().map(Enum::name).toArray(String[]::new))
            .accountLocked(!user.isActive())
            .disabled(!user.isActive())
            .build();
    }
}
