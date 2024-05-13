package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StepInfoRepository extends JpaRepository<StepInfo, Long> {
    Optional<StepInfo> findByOnboardeeAndStep(Onboardee onboardee, Step step);
    List<StepInfo> findByOnboardee(Onboardee onboardee);
}
