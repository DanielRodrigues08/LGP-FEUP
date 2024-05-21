package com.lifecycle.backend.controller;

import com.lifecycle.backend.dto.*;
import com.lifecycle.backend.model.*;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.repository.OnboardeeRepository;
import com.lifecycle.backend.repository.ProcessRepository;
import com.lifecycle.backend.repository.StepInfoRepository;
import com.lifecycle.backend.service.OnboardeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/onboardees")
public class OnboardeeController {

    @Autowired
    private OnboardeeRepository onboardeeRepository;

    @Autowired
    private OnboardeeService onboardeeService;

    // GET all onboardees
    @GetMapping
    public ResponseEntity<List<Onboardee>> getAllOnboardees() {
        List<Onboardee> onboardees = onboardeeRepository.findAll();
        return ResponseEntity.ok(onboardees);
    }

    // POST create onboardee
    @PostMapping
    public ResponseEntity<Object> createOnboardee(@RequestBody OnboardeeDTO onboardee) {

        Onboardee newOnboardee = new Onboardee(onboardee.getName(),
                onboardee.getPhoneNumber(),
                onboardee.getEmail(),
                onboardee.getGender(),
                onboardee.getNationality(),
                onboardee.getAnnualSalary(),
                onboardee.getPayrollNumber(),
                onboardee.getStartDate()
        );

        Onboardee savedOnboardee = onboardeeRepository.save(newOnboardee);

        if (onboardee.getProcessId() != null) {
            try {
                savedOnboardee = onboardeeService.updateOnboardeeProcess(savedOnboardee.getId(), onboardee.getProcessId());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(OnboardeeDTO.convertToDTO(savedOnboardee));
    }

    // GET onboardee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnboardeeById(@PathVariable Long id) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);

        if (onboardee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Onboardee _onboardee = onboardee.get();

        Process _process = _onboardee.getActiveProcess();
        if (_process == null) {
            return ResponseEntity.ok(Map.of("onboardee", OnboardeeDTO.convertToDTO(_onboardee)));
        } else {
            return ResponseEntity.ok(Map.of("onboardee", OnboardeeDTO.convertToDTO(_onboardee), "process", OnboardeeViewDTO.convertToDTO(_onboardee)));
        }
    }

    // PUT update onboardee
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOnboardee(@PathVariable Long id, @RequestBody OnboardeeDTO onboardeeRequest) {
        Optional<Onboardee> onboardeeOptional = onboardeeRepository.findById(id);
        if (!onboardeeOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Onboardee onboardeeToUpdate = onboardeeOptional.get();
        onboardeeToUpdate.setName(onboardeeRequest.getName());
        onboardeeToUpdate.setPhoneNumber(onboardeeRequest.getPhoneNumber());
        onboardeeToUpdate.setEmail(onboardeeRequest.getEmail());
        onboardeeToUpdate.setGender(onboardeeRequest.getGender());
        onboardeeToUpdate.setNationality(onboardeeRequest.getNationality());
        onboardeeToUpdate.setAnnualSalary(onboardeeRequest.getAnnualSalary());
        onboardeeToUpdate.setPayrollNumber(onboardeeRequest.getPayrollNumber());
        onboardeeToUpdate.setStartDate(onboardeeRequest.getStartDate());
        onboardeeToUpdate.setState(onboardeeRequest.getState());

        Onboardee updatedOnboardee = onboardeeRepository.save(onboardeeToUpdate);
        return ResponseEntity.ok(OnboardeeDTO.convertToDTO(updatedOnboardee));
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
        try {
            Onboardee savedOnboardee = onboardeeService.updateOnboardeeProcess(id, idProcess);
            return ResponseEntity.ok(OnboardeeDTO.convertToDTO(savedOnboardee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
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
            if (stepInfo.getStepInProcess().getProcess().getId() == _process.getId()) {
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
