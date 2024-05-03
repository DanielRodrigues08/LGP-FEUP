package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepInProcessRepository extends JpaRepository<StepInProcess, Long> {
    List<StepInProcess> findByProcessAndStep(Step step, Process process);
}