package com.lunifer.jo.fpshoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.lunifer.jo.fpshoppingcart.entity.User;
import com.lunifer.jo.fpshoppingcart.repository.UserRepository;
import com.lunifer.jo.fpshoppingcart.enums.UserRol;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Este es un endpoint público - no requiere autenticación";
    }

    @GetMapping("/generate-hash")
    public Map<String, String> generateHash() {
        Map<String, String> response = new HashMap<>();
        String password = "password123";
        String hashedPassword = passwordEncoder.encode(password);
        
        response.put("originalPassword", password);
        response.put("hashedPassword", hashedPassword);
        response.put("verification", String.valueOf(passwordEncoder.matches(password, hashedPassword)));
        
        return response;
    }

    @GetMapping("/list-users")
    public ResponseEntity<Map<String, Object>> listUsersWithRoles() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Obtener todos los usuarios
            List<User> users = userRepository.findAll();
            List<Map<String, Object>> userList = new ArrayList<>();
            
            for (User user : users) {
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("userId", user.getUserId());
                userInfo.put("username", user.getUsername());
                userInfo.put("email", user.getEmail());
                userInfo.put("active", user.isActive());
                
                // Obtener roles del usuario
                List<String> roles = user.getRoles().stream()
                    .map(role -> role.name())
                    .collect(Collectors.toList());
                userInfo.put("roles", roles);
                
                userList.add(userInfo);
            }
            
            result.put("status", "success");
            result.put("totalUsers", userList.size());
            result.put("users", userList);
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/create-admin-test")
    public ResponseEntity<Map<String, Object>> createAdminTestUser() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Verificar si ya existe el usuario de prueba
            Optional<User> existingUser = userRepository.findByUsername("testadmin");
            
            if (existingUser.isPresent()) {
                result.put("status", "exists");
                result.put("message", "Test admin user already exists");
                result.put("username", "testadmin");
                result.put("password", "admin123");
                return ResponseEntity.ok(result);
            }
            
            // Crear usuario ADMIN de prueba
            User testAdmin = new User();
            testAdmin.setUsername("testadmin");
            testAdmin.setEmail("testadmin@test.com");
            testAdmin.setFirstName("Test");
            testAdmin.setLastName("Admin");
            testAdmin.setPassword(passwordEncoder.encode("admin123"));
            testAdmin.setActive(true);
            testAdmin.setAddress("Test Address");
            testAdmin.setPhoneNumber("123456789");
            testAdmin.addRole(UserRol.ADMIN);
            
            User savedUser = userRepository.save(testAdmin);
            
            result.put("status", "created");
            result.put("message", "Test admin user created successfully");
            result.put("username", "testadmin");
            result.put("password", "admin123");
            result.put("userId", savedUser.getUserId());
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/update-all-passwords")
    public ResponseEntity<Map<String, Object>> updateAllPasswords() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Obtener todos los usuarios
            List<User> allUsers = userRepository.findAll();
            int updatedCount = 0;
            List<String> updatedUsernames = new ArrayList<>();
            
            for (User user : allUsers) {
                // Update password to password123
                user.setPassword(passwordEncoder.encode("password123"));
                userRepository.save(user);
                updatedCount++;
                updatedUsernames.add(user.getUsername());
            }
            
            result.put("status", "success");
            result.put("message", "All passwords updated successfully");
            result.put("totalUsersUpdated", updatedCount);
            result.put("newPassword", "password123");
            result.put("updatedUsernames", updatedUsernames);
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("error", e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }
}
