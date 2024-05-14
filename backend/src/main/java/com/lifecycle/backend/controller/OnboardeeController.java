package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.*;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.repository.OnboardeeRepository;
import com.lifecycle.backend.repository.ProcessRepository;
import com.lifecycle.backend.repository.StepInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/onboardees")
public class OnboardeeController {

    @Autowired
    private OnboardeeRepository onboardeeRepository;

    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private StepInfoRepository stepInfoRepository;

    // GET all onboardees
    @GetMapping
    public ResponseEntity<List<Onboardee>> getAllOnboardees() {
        List<Onboardee> onboardees = onboardeeRepository.findAll();
        return ResponseEntity.ok(onboardees);
    }

    // POST create onboardee
    @PostMapping
    public ResponseEntity<Onboardee> createOnboardee(@RequestBody Onboardee onboardee) {
        Onboardee savedOnboardee = onboardeeRepository.save(onboardee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOnboardee);
    }

    // GET onboardee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Onboardee> getOnboardeeById(@PathVariable Long id) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        return onboardee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT update onboardee
    @PutMapping("/{id}")
    public ResponseEntity<Onboardee> updateOnboardee(@PathVariable Long id, @RequestBody Onboardee onboardee) {
        if (!onboardeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        onboardee.setId(id); // Ensure ID is set for the update operation
        Onboardee updatedOnboardee = onboardeeRepository.save(onboardee);
        return ResponseEntity.ok(updatedOnboardee);
    }

    // DELETE onboardee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnboardee(@PathVariable Long id) {
        if (!onboardeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        onboardeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<Object> updateOnboardeeProcess(@PathVariable long id, @RequestBody long idProcess) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        if (onboardee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Onboardee not found"));
        }

        Onboardee _onboardee = onboardee.get();

        if (_onboardee.getActiveProcess() != null && _onboardee.getActiveProcess().getId() == idProcess) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Onboardee already has this process"));
        }

        Optional<Process> process = processRepository.findById(idProcess);
        if (process.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Process not found"));
        }
        Process _process = process.get();

        boolean addNewStepsInfo = true;

        for (StepInfo stepInfo : _onboardee.getStepsInfo()) {
            if (stepInfo.getStep().getProcess().getId() == idProcess) {
                stepInfo.setStatus(StepInfoStatus.NOT_STARTED);
                addNewStepsInfo = false;
            } else {
                stepInfo.setStatus(StepInfoStatus.ABORTED);
            }
            stepInfoRepository.save(stepInfo);
        }

        if (addNewStepsInfo) {
            for (StepInProcess stepInProcess : _process.getSteps()) {
                StepInfo stepInfo = new StepInfo();
                stepInfo.setOnboardee(_onboardee);
                stepInfo.setStep(stepInProcess);
                stepInfo.setStatus(StepInfoStatus.NOT_STARTED);
                stepInfoRepository.save(stepInfo);
            }
        }

        return ResponseEntity.ok(_onboardee);
    }


    @GetMapping("/{id}/process/active")
    public ResponseEntity<Object> getProcess(@PathVariable Long id) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        if (onboardee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Onboardee not found"));
        }
        Onboardee _onboardee = onboardee.get();

        Process _process = _onboardee.getActiveProcess();

        if (_process == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "The onboardee has no active process"));
        }

        List<StepInfo> stepsInfo = new ArrayList<>();
        for (StepInfo stepInfo : _onboardee.getStepsInfo()) {
            if (stepInfo.getStep().getProcess().getId() == _process.getId()) {
                stepsInfo.add(stepInfo);
            }
        }

        return ResponseEntity.ok(Map.of("process", _process, "stepsInfo", stepsInfo));

    }

    @GetMapping("/{id}/process/archived")
    public ResponseEntity<Object> getStepsInfoArchived(@PathVariable Long id) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        if (onboardee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Onboardee not found"));
        }
        Onboardee _onboardee = onboardee.get();

        List<StepInfo> stepsInfo = new ArrayList<>();
        for (StepInfo stepInfo : _onboardee.getStepsInfo()) {
            if (stepInfo.getStatus() == StepInfoStatus.ABORTED) {
                stepsInfo.add(stepInfo);
            }
        }

        return ResponseEntity.ok(stepsInfo);

    }


}
