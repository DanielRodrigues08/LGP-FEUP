package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.HRMember;
import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnboardeeRepository extends JpaRepository<Onboardee, Long> {
    Optional<Onboardee> findByUsername(String username);
    Optional<Onboardee> findById(Long id);
}
