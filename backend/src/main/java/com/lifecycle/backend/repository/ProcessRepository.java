package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Process;

import com.lifecycle.backend.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    List<Process> findByTitle(String title);
}