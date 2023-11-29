package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private PasswordEncoder passwordE;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Bean
    PasswordEncoder passwordE() {
        this.passwordE = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return this.passwordE;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUserEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userEntityToUserDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper.INSTANCE::userEntityToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return UserMapper.INSTANCE.userEntityToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow();

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setAddress(userDTO.getPhoneNumber());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setUsername(userDTO.getUserName());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setActive(userDTO.isActive());
        existingUser.setOrderHistory(userDTO.getOrderHistory());
        existingUser.setReviewHistory(userDTO.getReviewHistory());

        User updatedUser = userRepository.save(existingUser);

        return UserMapper.INSTANCE.userEntityToUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Querying the user: " + username);

        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

        for (String role : user.getRoles()) {
            logger.info("Role: "+role);
            roles.add(new SimpleGrantedAuthority(role));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public void createUser(@NonNull String username, @NonNull String password, @NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String address, @NonNull String phoneNumber) {
        User user = new User();
        List<String> roles = Arrays.asList("USER", "ADMIN");
        user.setUsername(username);
        user.setPassword(passwordE.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(roles);
        this.userRepository.save(user);
    }

    public void initializeUser() {
        this.createUser("ada", "ada","Ada", "Martinez", "adamartinez@gmail.com","Rep Dom", "809-667-8998");
        this.createUser("maria", "maria","Maria", "Perez", "mariaperez@gmail.com","Rep Dom", "809-893-3489");
    }

}

