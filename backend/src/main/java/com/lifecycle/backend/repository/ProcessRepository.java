package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Process;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    List<Process> findByTitle(String title);
}