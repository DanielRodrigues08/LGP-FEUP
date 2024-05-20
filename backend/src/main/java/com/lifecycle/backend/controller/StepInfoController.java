package com.lifecycle.backend.controller;

import com.lifecycle.backend.dto.StepInfoDTO;
import com.lifecycle.backend.model.*;
import com.lifecycle.backend.repository.StepInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/steps-info")
@Secured({"HR", "ADMIN"})
public class StepInfoController {

    @Autowired
    private StepInfoRepository stepInfoRepository;

    @GetMapping
    public ResponseEntity<Object> getAllStepInfo() {
        List<StepInfo> stepInfo = stepInfoRepository.findAll();
        List<StepInfoDTO> stepInfoDTOs = new ArrayList<>();
        for (StepInfo step : stepInfo) {
            stepInfoDTOs.add(StepInfoDTO.convertToDTO(step));
        }

        return new ResponseEntity<>(stepInfoDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStepInfo(@PathVariable Long id) {
        Optional<StepInfo> stepInfo = stepInfoRepository.findById(id);
        if (stepInfo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(StepInfoDTO.convertToDTO(stepInfo.get()));
    }

    @PatchMapping("/{id}")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<Object> updateStepInfo(@PathVariable Long id, @RequestBody StepInfoDTO stepInfoDTO) {
        Optional<StepInfo> stepInfo = stepInfoRepository.findById(id);
        if (stepInfo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        StepInfo stepInfoToUpdate = stepInfo.get();

        if (stepInfoToUpdate.getStatus() == StepInfoStatus.ABORTED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot update an aborted step"));
        } else if (stepInfoToUpdate.getStatus() == StepInfoStatus.COMPLETED) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot update a completed step"));
        }

        if (stepInfoDTO.getDescription() != null) {
            stepInfoToUpdate.setDescription(stepInfoDTO.getDescription());
        }

        if (stepInfoDTO.getStatus() != null) {
            StepInfoStatus newStatus = stepInfoDTO.getStatus();

            if (newStatus == StepInfoStatus.ABORTED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot set a step to aborted"));
            } else if (newStatus == StepInfoStatus.COMPLETED) {
                List<StepInProcess> dependencies = stepInfoToUpdate.getStepInProcess().getDependencies();
                for (StepInProcess dependency : dependencies) {
                    Optional<StepInfo> dependencyStepInfo = stepInfoRepository.findByStepInProcessAndOnboardee(dependency, stepInfoToUpdate.getOnboardee());
                    if (dependencyStepInfo.isEmpty() || dependencyStepInfo.get().getStatus() != StepInfoStatus.COMPLETED) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot complete a step with incomplete dependencies"));
                    }
                }
            }

            stepInfoToUpdate.setStatus(newStatus);
        }

        StepInfo updatedStepInfo = stepInfoRepository.save(stepInfoToUpdate);
        return ResponseEntity.ok(StepInfoDTO.convertToDTO(updatedStepInfo));
    }
}
