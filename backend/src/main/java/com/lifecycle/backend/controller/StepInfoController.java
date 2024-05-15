package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.*;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.repository.OnboardeeRepository;
import com.lifecycle.backend.repository.StepInfoRepository;
import com.lifecycle.backend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class StepInfoController {

    @Autowired
    private OnboardeeRepository onboardeeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private StepInfoRepository stepInfoRepository;

    @PostMapping("/onboardees/{onboardeeId}/steps/{stepId}")
    public ResponseEntity<Object> createStepInfo(@PathVariable Long onboardeeId, @PathVariable Long stepId, @RequestBody StepInfo stepInfo) {
        Map<String, Object> response = new HashMap<>();

        Optional<Onboardee> onboardee = onboardeeRepository.findById(onboardeeId);
        if (onboardee.isEmpty()) {
            response.put("message", "Onboardee not found");
            response.put("onboardeeId", onboardeeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Optional<Step> step = stepRepository.findById(stepId);
        if (step.isEmpty()) {
            response.put("message", "Step not found");
            response.put("stepId", stepId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Onboardee _onboardee = onboardee.get();
        Step _step = step.get();

        if (!_onboardee.getProcess().getSteps().stream()
                .map(StepInProcess::getStep)
                .toList().contains(_step)) {
            response.put("message", "Step not found in the onboardee's process");
            response.put("stepId", stepId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<StepInfo> existingStepInfo = stepInfoRepository.findByOnboardeeAndStep(_onboardee, _step);
        if (existingStepInfo.isPresent()) {
            response.put("message", "Step info already exists");
            response.put("onboardeeId", onboardeeId);
            response.put("stepId", stepId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // TODO: Check if the dependent steps are completed

        stepInfo.setOnboardee(_onboardee);
        stepInfo.setStep(_step);
        StepInfo savedStepInfo = stepInfoRepository.save(stepInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStepInfo);
    }

    @GetMapping("/onboardees/{onboardeeId}/steps/{stepId}")
    public ResponseEntity<Object> getStepInfo(@PathVariable Long onboardeeId, @PathVariable Long stepId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Onboardee> onboardee = onboardeeRepository.findById(onboardeeId);
        if (onboardee.isEmpty()) {
            response.put("message", "Onboardee not found");
            response.put("onboardeeId", onboardeeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Optional<Step> step = stepRepository.findById(stepId);
        if (step.isEmpty()) {
            response.put("message", "Step not found");
            response.put("stepId", stepId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Onboardee _onboardee = onboardee.get();
        Step _step = step.get();
        Optional<StepInfo> stepInfo = stepInfoRepository.findByOnboardeeAndStep(_onboardee, _step);
        if (stepInfo.isEmpty()) {
            response.put("message", "Step info not found");
            response.put("onboardeeId", onboardeeId);
            response.put("stepId", stepId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(stepInfo.get());
    }

    @PatchMapping("/onboardees/{onboardeeId}/steps/{stepId}")
    public ResponseEntity<Object> updateStepInfo(@PathVariable Long onboardeeId, @PathVariable Long stepId, @RequestBody Map<String, Object> body) {

        ResponseEntity<Object> stepInfoResponse = this.getStepInfo(onboardeeId, stepId);
        if (stepInfoResponse.getStatusCode() != HttpStatus.OK) {
            return stepInfoResponse;
        }

        StepInfo stepInfo = (StepInfo) stepInfoResponse.getBody();

        try {
            if (body.containsKey("status")) {
                StepInfoStatus status = StepInfoStatus.valueOf((String) body.get("status"));
                stepInfo.setStatus(status);
            }

            if (body.containsKey("description")) {
                stepInfo.setDescription((String) body.get("description"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid request"));
        }

        StepInfo savedStepInfo = stepInfoRepository.save(stepInfo);
        return ResponseEntity.ok(savedStepInfo);
    }


    @DeleteMapping("/onboardees/{onboardeeId}/steps/{stepId}")
    public ResponseEntity<Object> deleteStepInfo(@PathVariable Long onboardeeId, @PathVariable Long stepId) {
        ResponseEntity<Object> stepInfoResponse = this.getStepInfo(onboardeeId, stepId);
        if (stepInfoResponse.getStatusCode() != HttpStatus.OK) {
            return stepInfoResponse;
        }

        StepInfo stepInfo = (StepInfo) stepInfoResponse.getBody();
        stepInfoRepository.delete(stepInfo);
        return ResponseEntity.noContent().build();
    }
}
