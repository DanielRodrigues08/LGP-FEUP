package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.HRMember;
import com.lifecycle.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    // Additional methods for specific user types
    Optional<Onboardee> findOnboardeeById(Long id);
    Optional<HRMember> findHRMemberById(Long id);
    // Add more methods as needed
}
