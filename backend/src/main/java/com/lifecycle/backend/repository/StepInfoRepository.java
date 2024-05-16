package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.*;
import com.lifecycle.backend.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StepInfoRepository extends JpaRepository<StepInfo, Long> {
    Optional<StepInfo> findByStepInProcessAndOnboardee(StepInProcess step, Onboardee onboardee);
}
