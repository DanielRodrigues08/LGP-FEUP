package com.lifecycle.backend.controller;

import com.lifecycle.backend.dto.StepInProcessDTO;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/step-in-processes")
@Secured({"HR", "ADMIN"})
public class StepInProcessController {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private StepInProcessRepository stepInProcessRepository;

    @GetMapping
    public ResponseEntity<Object> getAllStepInProcesses() {
        List<StepInProcess> stepInProcesses = stepInProcessRepository.findAll();
        List<StepInProcessDTO> stepInProcessDTOs = new ArrayList<>();
        for (StepInProcess stepInProcess : stepInProcesses) {
            stepInProcessDTOs.add(StepInProcessDTO.convertToDTO(stepInProcess));
        }
        return new ResponseEntity<>(stepInProcessDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createStepInProcess(@RequestBody StepInProcessDTO stepInProcessDTO) {
        if (stepInProcessDTO.getProcessId() == null || stepInProcessDTO.getStepId() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Missing process id or step id"));
        }

        Optional<Process> process = processRepository.findById(stepInProcessDTO.getProcessId());
        if (process.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Process _process = process.get();

        Optional<Step> step = stepRepository.findById(stepInProcessDTO.getStepId());
        if (step.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Step _step = step.get();

        List<StepInProcess> dependencies = new ArrayList<>();
        if (stepInProcessDTO.getDependencies() != null) {
            for (Long dependencyId : stepInProcessDTO.getDependencies()) {
                Optional<StepInProcess> dependency = stepInProcessRepository.findById(dependencyId);
                if (dependency.isEmpty()) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Invalid dependency id"));
                }
                if (!dependency.get().getProcess().equals(_process)) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Dependencies must be in the same process"));
                }
                dependencies.add(dependency.get());
            }
        }

        try {
            StepInProcess stepInProcess = new StepInProcess(_step, _process, dependencies);
            StepInProcess savedStepInProcess = stepInProcessRepository.save(stepInProcess);
            return new ResponseEntity<>(StepInProcessDTO.convertToDTO(savedStepInProcess), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid dependencies"));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateStepInProcess(@PathVariable("id") long id, @RequestBody StepInProcessDTO stepInProcessDTO) {
        Optional<StepInProcess> stepInProcess = stepInProcessRepository.findById(id);
        if (stepInProcess.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        StepInProcess _stepInProcess = stepInProcess.get();

        if (stepInProcessDTO.getDependencies() != null) {
            _stepInProcess.setDependencies(new ArrayList<>());
            List<StepInProcess> dependencies = new ArrayList<>();
            for (Long dependencyId : stepInProcessDTO.getDependencies()) {
                Optional<StepInProcess> dependency = stepInProcessRepository.findById(dependencyId);
                if (dependency.isEmpty()) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Invalid dependency id"));
                }
                if (!dependency.get().getProcess().equals(_stepInProcess.getProcess())) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Dependencies must be in the same process"));
                }
                dependencies.add(dependency.get());
            }
            _stepInProcess.setDependencies(dependencies);
        }

        try {
            StepInProcess updatedStepInProcess = stepInProcessRepository.save(_stepInProcess);
            return new ResponseEntity<>(StepInProcessDTO.convertToDTO(updatedStepInProcess), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid dependencies"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStepInProcess(@PathVariable("id") long id) {
        Optional<StepInProcess> stepInProcess = stepInProcessRepository.findById(id);
        if (stepInProcess.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        stepInProcessRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

