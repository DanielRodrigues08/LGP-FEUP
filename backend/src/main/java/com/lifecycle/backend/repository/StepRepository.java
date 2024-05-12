package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Step;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long> {
    Optional<Step> findById(Long id);
    List<Step> findByTitle(String title);
    List<Step> findByDescription(String description);
}