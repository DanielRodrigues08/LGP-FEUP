package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
