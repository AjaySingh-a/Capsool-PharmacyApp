package com.capsool.controller;

import com.capsool.model.Role;
import com.capsool.model.User;
import com.capsool.repository.UserRepository;
import com.capsool.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (user.getRole() == null || !user.getRole().equals(Role.PHARMACY_OWNER)) {
            return ResponseEntity.badRequest().body("{\"error\": \"Only Pharmacy Owners can register!\"}");
        }

        // ✅ Password Hashing
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("{\"message\": \"Pharmacy Owner registered successfully!\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent()
                && existingUser.get().getRole().equals(Role.PHARMACY_OWNER)
                && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {

            try {
                String token = jwtUtil.generateToken(user.getUsername(), "PHARMACY_OWNER");
                return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
            } catch (Exception e) {
                return ResponseEntity.status(500).body("{\"error\": \"Token generation failed\"}");
            }
        }
        return ResponseEntity.badRequest().body("{\"error\": \"Invalid credentials or not a Pharmacy Owner!\"}");
    }
}
