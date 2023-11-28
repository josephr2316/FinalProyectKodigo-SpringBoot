package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.UserDTO;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.UserMapper;
import com.lunifer.jo.fpshoppingcart.payload.UserResponse;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.userDTOToUserEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.userEntityToUserDTO(savedUser);
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize) {
        // Create a Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize);

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
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return UserMapper.INSTANCE.userEntityToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setAddress(userDTO.getPhoneNumber());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setUserRol(userDTO.getUserRol());
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

}
