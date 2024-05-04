package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Onboardee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnboardeeRepository extends JpaRepository<Onboardee, Long> {
    Optional<Onboardee> findById(Long id);
}
