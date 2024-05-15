package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.StepInfo;
import com.lifecycle.backend.repository.OnboardeeRepository;
import com.lifecycle.backend.repository.ProcessRepository;
import com.lifecycle.backend.repository.StepInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.time.LocalDate;
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
        System.out.println("aquiiiiii");
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

    @GetMapping("/{id}/process")
    public ResponseEntity<Process> getProcess(@PathVariable Long id) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        if (onboardee.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(onboardee.get().getProcess(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/process")
    public ResponseEntity<Object> updateProcess(@PathVariable Long id, @RequestBody Map<String, Object> patch) {
        if (!patch.containsKey("process_id")) {
            return new ResponseEntity<>(Map.of("message", "Process not found!"), HttpStatus.NOT_FOUND);
        }

        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        if (onboardee.isEmpty()) {
            return new ResponseEntity<>(Map.of("message", "Onboardee not found!"), HttpStatus.NOT_FOUND);
        }

        Onboardee _onboardee = onboardee.get();

        Process _process;

        if (patch.get("process_id") == null) {
            _process = null;
        } else {
            long process_id;
            try {
                process_id = Long.parseLong(patch.get("process_id").toString());
            } catch (Exception e) {
                return new ResponseEntity<>(Map.of("message", "Invalid process ID!"), HttpStatus.BAD_REQUEST);
            }

            Optional<Process> process = processRepository.findById(process_id);

            if (process.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            _process = process.get();
        }

        if (_onboardee.getProcess().equals(_process)) {
            return ResponseEntity.ok().build();
        }

        List<StepInfo> stepInfos = stepInfoRepository.findByOnboardee(_onboardee);
        stepInfoRepository.deleteAll(stepInfos);

        _onboardee.setProcess(_process);
        onboardeeRepository.save(_onboardee);

        return ResponseEntity.ok().build();
    }
}
