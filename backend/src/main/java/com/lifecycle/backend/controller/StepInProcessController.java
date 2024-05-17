package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/step-in-processes")
public class StepInProcessController {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private StepInProcessRepository stepInProcessRepository;

    @GetMapping
    public ResponseEntity<Object> getAllStepInProcesses(){
        List<StepInProcess> stepInProcesses = stepInProcessRepository.findAll();
        return new ResponseEntity<>(stepInProcesses, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Object> createStepInProcess(@RequestBody Map<String, Object> body) {
        try {
            long processId = ((Number) body.get("process_id")).longValue();
            long stepId = ((Number) body.get("step_id")).longValue();

            Optional<Process> process = processRepository.findById(processId);
            if (process.isEmpty()) {
                throw new IllegalArgumentException("Invalid process id");
            }

            Optional<Step> step = stepRepository.findById(stepId);
            if (step.isEmpty()) {
                throw new IllegalArgumentException("Invalid step id");
            }

            Process _process = process.get();
            Step _step = step.get();

            if (stepInProcessRepository.findByProcessAndStep(_process, _step).isPresent()) {
                throw new IllegalArgumentException("Step already exists in process");
            }

            StepInProcess stepInProcess = new StepInProcess();
            stepInProcess.setProcess(_process);
            stepInProcess.setStep(_step);
            stepInProcessRepository.save(stepInProcess);

            return new ResponseEntity<>(stepInProcess, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
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

    @PatchMapping("/{id}/dependencies")
    public ResponseEntity<Object> updateDependencies(@PathVariable("id") long id, @RequestBody Map<String, Object> body) {
        long dependencyId;
        try {
            dependencyId = ((Number) body.get("dependency_id")).longValue();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid dependency id type"));
        }

        if(id == dependencyId){
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot add a step as a dependency of itself"));
        }

        Optional<StepInProcess> stepInProcess = stepInProcessRepository.findById(id);
        if (stepInProcess.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        StepInProcess _stepInProcess = stepInProcess.get();

        Optional<StepInProcess> dependency = stepInProcessRepository.findById(dependencyId);
        if (dependency.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid dependency id"));
        }
        StepInProcess _dependency = dependency.get();

        if (!_stepInProcess.getProcess().equals(_dependency.getProcess())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Steps are not in the same process"));
        }

        try {
            String op = (String) body.get("operation");
            if (op.equals("add") && !_stepInProcess.getDependencies().contains(_dependency)) {
                _stepInProcess.getDependencies().add(_dependency);
            } else if (op.equals("remove")) {
                _stepInProcess.getDependencies().remove(_dependency);
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid operation"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid operation type"));
        }

        StepInProcess updatedStepInProcess = stepInProcessRepository.save(_stepInProcess);
        return new ResponseEntity<>(updatedStepInProcess, HttpStatus.OK);
    }
}

