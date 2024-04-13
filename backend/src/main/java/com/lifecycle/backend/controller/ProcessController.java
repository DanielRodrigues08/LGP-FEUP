package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.repository.ProcessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProcessController {

    @Autowired
    ProcessRepository processRepository;

    @GetMapping("/processes")
    public ResponseEntity<List<Process>> getAllProcesses(@RequestParam(required = false) String title) {
        try {
            List<Process> processes = new ArrayList<Process>();

            if (title == null)
                processes.addAll(processRepository.findAll());
            else
                processes.addAll(processRepository.findByTitle(title));

            if (processes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/processes/{id}")
    public ResponseEntity<Process> getProcessById(@PathVariable("id") long id) {
        Optional<Process> processData = processRepository.findById(id);

        return processData.map(process -> new ResponseEntity<>(process, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/processes/create")
    public ResponseEntity<Process> getProcessCreationForm() {
        return null;
    }

    @PostMapping("/processes/create")
    public ResponseEntity<Process> createProcess(@RequestBody Process process) {
        return null;
    }

    @PutMapping("/processes/{id}")
    public ResponseEntity<Process> updateProcess(@PathVariable("id") long id, @RequestBody Process tutorial) {
        return null;
    }

    @DeleteMapping("/processes/{id}")
    public ResponseEntity<HttpStatus> deleteProcess(@PathVariable("id") long id) {
        return null;
    }

}
