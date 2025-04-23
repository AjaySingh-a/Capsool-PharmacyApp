package com.capsool.controller;

import com.capsool.model.User;
import com.capsool.repository.UserRepository;
import com.capsool.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // ✅ JWT Authentication Required - Fetch User Profile
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        String username = principal.getName();
        return userRepository.findByUsername(username)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("error", "❌ User not found!")));
    }

    // ✅ Only PHARMACY_OWNER Can Access Dashboard
    @PreAuthorize("hasAuthority('ROLE_PHARMACY_OWNER')")
    @GetMapping("/pharmacy-dashboard")
    public ResponseEntity<?> getPharmacyDashboard() {
        return ResponseEntity.ok("🚀 Welcome, PHARMACY OWNER! Here is your dashboard.");
    }
}
