package com.lifecycle.backend.controller;

import com.lifecycle.backend.dto.ProcessDTO;
import com.lifecycle.backend.dto.StepDTO;
import com.lifecycle.backend.dto.StepInProcessDTO;
import com.lifecycle.backend.model.*;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.incoming.IncomingProcess;
import com.lifecycle.backend.model.incoming.IncomingStep;
import com.lifecycle.backend.repository.ProcessRepository;

import com.lifecycle.backend.repository.StepInProcessRepository;
import com.lifecycle.backend.repository.StepRepository;
import com.lifecycle.backend.repository.UserRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/processes")
@Secured({"EMPLOYEE", "HR", "ADMIN"})
public class ProcessController {

    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StepRepository stepRepository;
    @Autowired
    private StepInProcessRepository stepInProcessRepository;

    @Autowired
    private StepController stepController;
    @Autowired
    private StepInProcessController stepInProcessController;

    @GetMapping("")
    public ResponseEntity<Object> getAllProcesses() {
        List<Process> processes = processRepository.findAll();
        List<ProcessDTO> processesDTO = new ArrayList<>();
        for (Process process : processes) {
            processesDTO.add(ProcessDTO.convertToDTO(process));
        }
        return ResponseEntity.ok(processesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProcessById(@PathVariable("id") long id) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProcessDTO.convertToDTO(process.get()));
    }

    @PostMapping("/create")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<Object> createProcess(@RequestBody ProcessDTO processDTO) {
        try {
            Process process = new Process(processDTO.getTitle(), processDTO.getDescription());
            Process processSaved = processRepository.save(process);
            return ResponseEntity.ok(ProcessDTO.convertToDTO(processSaved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/create-with-steps")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<Object> createProcessWithSteps(@RequestBody IncomingProcess incomingProcess) {
        try {
            // for future use in updating the process (since body of code will be similar), check if incoming process already exists in the database here
            Process process = new Process(incomingProcess.getTitle(), incomingProcess.getDescription());
            Process _process = processRepository.save(process);

            // maps step position in process to ID (used for new steps)
            Map<Integer, Long> stepMap = new HashMap<>();

            System.out.println("Reached step creation");
            Step _step;
            int position = 1;
            for (IncomingStep incomingStep : incomingProcess.getIncomingSteps()) {
                Long _stepId = incomingStep.getId();
                // is new step, create Step entity before making StepInProcess
                if (_stepId == null) {
                    Optional<User> owner = userRepository.findById(incomingStep.getOwner());
                    Optional<User> backup = userRepository.findById(incomingStep.getBackup());
                    if (owner.isEmpty() || backup.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner/Backup not found.");
                    _step = new Step(incomingStep, owner.get(), backup.get());
                    StepDTO _stepCreationResponse = (StepDTO) stepController.createStep(StepDTO.convertToDTO(_step)).getBody();
                    _stepId = _stepCreationResponse.getId();
                }
                Optional<Step> foundStep = stepRepository.findById(_stepId);
                if (foundStep.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Step '" + incomingStep.getTitle() + "' not found.");
                _step = foundStep.get();
                stepMap.put(position, _stepId);

                // if new step has dependencies
                List<StepInProcess> newStep_Dependencies = new ArrayList<>();
                if (!incomingStep.getDependencies().isEmpty()) {
                    for (Integer dependencyPosition : incomingStep.getDependencies()) {
                        System.out.println("dependency ID: " + dependencyPosition);
                        Optional<Step> dependentStep = stepRepository.findById(stepMap.get(dependencyPosition));
                        if (dependentStep.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Step '" + incomingStep.getTitle() + "' is dependent on a non-existing step.");
                        Optional<StepInProcess> dependentStepInProcess = stepInProcessRepository.findByProcessAndStep(_process, dependentStep.get());
                        if (dependentStepInProcess.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Step '" + incomingStep.getTitle() + "' is dependent on a step not assigned to this process.");
                        newStep_Dependencies.add(dependentStepInProcess.get());
                    }
                }

                StepInProcess newStepInProcess = new StepInProcess(_step, _process, newStep_Dependencies);
                stepInProcessController.createStepInProcess(StepInProcessDTO.convertToDTO(newStepInProcess));
                position++;
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<Object> updateProcess(@PathVariable long id, @RequestBody ProcessDTO processDTO) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Process _process = process.get();

        if (processDTO.getTitle() != null) _process.setTitle(processDTO.getTitle());
        if (processDTO.getDescription() != null) _process.setDescription(processDTO.getDescription());

        try {
            Process processSaved = processRepository.save(_process);
            return ResponseEntity.ok(ProcessDTO.convertToDTO(processSaved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }
}
