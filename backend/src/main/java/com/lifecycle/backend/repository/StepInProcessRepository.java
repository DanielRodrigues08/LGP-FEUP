package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.StepInProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StepInProcessRepository extends JpaRepository<StepInProcess, Long> {
    Optional<StepInProcess> findByProcessAndStep(Process process, Step step);
}