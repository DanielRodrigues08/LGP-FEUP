package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.repository.ProcessRepository;

import com.lifecycle.backend.repository.StepInProcessRepository;
import com.lifecycle.backend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/processes")
public class ProcessController {

    @Autowired
    private ProcessRepository processRepository;

    @GetMapping("")
    public ResponseEntity<Object> getAllProcesses() {
        List<Process> processes = processRepository.findAll();
        return ResponseEntity.ok(processes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProcessById(@PathVariable("id") long id) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(process.get());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProcess(@RequestBody Process process) {
        processRepository.save(process);
        return ResponseEntity.ok(process);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProcess(@PathVariable long id, @RequestBody Map<String, Object> patch) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Process _process = process.get();
        try {
            if (patch.containsKey("title")) _process.setTitle((String) patch.get("title"));
            if (patch.containsKey("description")) _process.setDescription((String) patch.get("description"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid patch request");
        }

        processRepository.save(_process);
        return ResponseEntity.ok(_process);
    }
}
