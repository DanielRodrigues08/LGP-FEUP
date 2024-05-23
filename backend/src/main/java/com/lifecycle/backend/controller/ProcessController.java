package com.lifecycle.backend.controller;

import com.lifecycle.backend.dto.ProcessDTO;
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
