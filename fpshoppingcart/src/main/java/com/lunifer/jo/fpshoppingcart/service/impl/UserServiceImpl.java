package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.payload.UserResponse;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUserEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userEntityToUserDTO(savedUser);
    }

    @Override
    @Transactional
    public UserResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // Sorting Support
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Retrieve a page of users
        Page<User> userList = userRepository.findAll(pageable);

        // Get content for user object
        List<User> listOfUsers = userList.getContent();

        List<UserDTO> content = listOfUsers.stream()
                .map(UserMapper.INSTANCE::userEntityToUserDTO)
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(userList.getNumber());
        userResponse.setPageSize(userList.getSize());
        userResponse.setTotalElements(userList.getTotalElements());
        userResponse.setTotalPages(userList.getTotalPages());
        userResponse.setLast(userList.isLast());
        return userResponse;
    }

    @Override
    @Transactional
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return UserMapper.INSTANCE.userEntityToUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Querying the user: " + username);

        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }

        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : user.getRoles()) {
            logger.info("ROLE_"+role);
            roles.add(new SimpleGrantedAuthority("ROLE_"+role));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
    @Override
    @Transactional
    public String disableEnableUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(!user.isActive());

            // Since we're using @Transactional, changes will be automatically
            // saved to the database when the transaction is committed

            // Return a message indicating whether the user was successfully disabled or enabled
            return "User: " + user.getUsername() +
                    " with ID: " + user.getUserId() +
                    " has been successfully " + (user.isActive() ? "enabled" : "disabled");
        } else {
            throw new EntityNotFoundException("Cannot find user with ID " + userId);
        }
    }

}

