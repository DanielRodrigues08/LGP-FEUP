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

// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/processes")
public class ProcessController {

    @Autowired
    ProcessRepository processRepository;
    @Autowired
    StepRepository stepRepository;
    @Autowired
    private StepInProcessRepository stepInProcessRepository;

    @GetMapping("")
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

    @GetMapping("/{id}")
    public ResponseEntity<Process> getProcessById(@PathVariable("id") long id) {
        Optional<Process> processData = processRepository.findById(id);

        return processData.map(process -> new ResponseEntity<>(process, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Process> createProcess(@RequestBody Process process) {
        processRepository.save(process);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProcess(@PathVariable("id") long id) {
        try {
            processRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Process> updateProcess(@PathVariable("id") long id, @RequestBody Map<String, Object> patch) {
        Optional<Process> processToChange = processRepository.findById(id);

        if (processToChange.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Process _process = processToChange.get();
            if (patch.containsKey("title")) _process.setTitle((String) patch.get("title"));
            if (patch.containsKey("description")) _process.setDescription((String) patch.get("description"));
            return new ResponseEntity<>(processRepository.save(_process), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/onboardees")
    public ResponseEntity<List<Onboardee>> getOnboardeesByProcess(@PathVariable("id") long id) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Process _process = process.get();

        return new ResponseEntity<>(_process.getOnboardees(), HttpStatus.OK);
    }
}
