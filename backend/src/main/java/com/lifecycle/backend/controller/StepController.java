package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class StepController {

    @Autowired
    StepRepository stepRepository;

    @GetMapping("/steps")
    public ResponseEntity<List<Step>> getAllSteps(@RequestParam(required = false) String title) {
        try {
            List<Step> processes = new ArrayList<Step>();

            if (title == null)
                processes.addAll(stepRepository.findAll());
            else
                processes.addAll(stepRepository.findByTitle(title));

            if (processes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(processes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/steps/{id}")
    public ResponseEntity<Step> getStepById(@PathVariable("id") long id) {
        Optional<Step> stepData = stepRepository.findById(id);

        if (stepData.isPresent()) {
            return new ResponseEntity<>(stepData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/steps/test")
    public ResponseEntity<Step> testStep() {
        Step step = Step.builder().title("testStep").build();
        System.out.println(step);

        if (step != null) {
            return new ResponseEntity<>(step, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/steps/create")
    public ResponseEntity<Step> getStepCreationForm() {
        return null;
    }

    @PostMapping("/steps/create")
    public ResponseEntity<Step> createStep(@RequestBody Step step) {
        return null;
    }

    @PutMapping("/steps/{id}")
    public ResponseEntity<Step> updateStep(@PathVariable("id") long id, @RequestBody Step step) {
        return null;
    }

    @DeleteMapping("/steps/{id}")
    public ResponseEntity<HttpStatus> deleteStep(@PathVariable("id") long id) {
        return null;
    }

}
