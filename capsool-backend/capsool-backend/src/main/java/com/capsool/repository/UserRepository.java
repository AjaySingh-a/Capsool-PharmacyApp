package com.capsool.repository;

import com.capsool.model.Role;
import com.capsool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // ✅ Fetch only Pharmacy Owners (CUSTOMER role removed)
    List<User> findByRole(Role role);
}
