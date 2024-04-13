package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.repository.ProcessRepository;

import com.lifecycle.backend.repository.StepRepository;
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
@RequestMapping("/processes")
public class ProcessController {

    @Autowired
    ProcessRepository processRepository;
    @Autowired
    StepRepository stepRepository;

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
    
    @GetMapping("/create")
    public ResponseEntity<Process> getProcessCreationForm() {
        return null;
    }

    /* ---- PROCESS CRUD ---- */

    // Process Creation
    @PostMapping("/create")
    public ResponseEntity<Process> createProcess(@RequestBody Process process, @RequestBody List<Long> step_ids) {

        List<Step> stepList = new ArrayList<>();
        for (Long id : step_ids) {
            Optional<Step> step = stepRepository.findById(id);
            step.ifPresent(stepList::add);
        }
        process.setSteps(stepList);
        processRepository.save(process);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }

    /*@PostMapping("/processes/create")
    public ResponseEntity<Process> createProcess(@RequestBody Process process, @RequestBody List<Step> steps) {
        processRepository.save(process);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Process> updateProcess(@PathVariable("id") long id, @RequestBody Process step) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProcess(@PathVariable("id") long id) {
        return null;
    }

}
