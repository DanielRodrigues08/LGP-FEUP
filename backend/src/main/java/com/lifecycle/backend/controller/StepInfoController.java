package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.*;
import com.lifecycle.backend.repository.StepInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/steps-info")
public class StepInfoController {

    @Autowired
    private StepInfoRepository stepInfoRepository;

    @GetMapping
    public ResponseEntity<Object> getAllStepInfo() {
        List<StepInfo> stepInfo = stepInfoRepository.findAll();
        return new ResponseEntity<>(stepInfo, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStepInfo(@PathVariable Long id) {
        Optional<StepInfo> stepInfo = stepInfoRepository.findById(id);
        if (stepInfo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(stepInfo.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateStepInfo(@PathVariable Long id, @RequestBody Map<String, Object> patch) {
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

        if (patch.containsKey("description")) {
            if (patch.get("description") instanceof String) {
                stepInfoToUpdate.setDescription((String) patch.get("description"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "The description must be a string"));
            }
        }

        if (patch.containsKey("status")) {
            StepInfoStatus newStatus;
            try {
                newStatus = StepInfoStatus.valueOf((String) patch.get("status"));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid status"));
            }

            if (newStatus == StepInfoStatus.ABORTED) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot set a step to aborted"));
            } else if (newStatus == StepInfoStatus.COMPLETED) {
                List<StepInProcess> dependencies = stepInfoToUpdate.getStepInProcess().getDependencies();
                for (StepInProcess dependency : dependencies) {
                    Optional<StepInfo> dependencyStepInfo = stepInfoRepository.findByStepAndOnboardee(dependency, stepInfoToUpdate.getOnboardee());
                    if (dependencyStepInfo.isEmpty() || dependencyStepInfo.get().getStatus() != StepInfoStatus.COMPLETED) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Cannot complete a step with incomplete dependencies"));
                    }
                }
            }

            stepInfoToUpdate.setStatus(newStatus);
        }

        StepInfo updatedStepInfo = stepInfoRepository.save(stepInfoToUpdate);
        return ResponseEntity.ok(updatedStepInfo);
    }
}
