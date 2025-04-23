package com.capsool.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // ✅ PHARMACY_OWNER, ADMIN, etc.

    @Column(nullable = true)
    private String pharmacyName; // ✅ Only for PHARMACY_OWNER

    @Column(nullable = true)
    private String address; // ✅ Pharmacy address

    @Column(nullable = true)
    private String contactNumber; // ✅ Contact details
}
