package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Step;
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
@RequestMapping("/steps")
public class StepController {

    @Autowired
    StepRepository stepRepository;

    @GetMapping("")
    public ResponseEntity<List<Step>> getAllSteps(@RequestParam(required = false) String title) {
        try {
            List<Step> steps = new ArrayList<>();

            if (title == null)
                steps.addAll(stepRepository.findAll());
            else
                steps.addAll(stepRepository.findByTitle(title));

            if (steps.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(steps, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Step> getStepById(@PathVariable("id") long id) {
        Optional<Step> stepData = stepRepository.findById(id);

        return stepData.map(step -> new ResponseEntity<>(step, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public ResponseEntity<Step> getStepCreationForm() {
        return null;
    }

    /* ---- STEP CRUD ---- */

    // Step Creation
    @PostMapping("/create")
    public ResponseEntity<Step> createStep(@RequestBody Step step) {
        stepRepository.save(step);
        return new ResponseEntity<>(step, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Step> updateStep(@PathVariable("id") long id, @RequestBody Step step) {
        Optional<Step> stepToChange = stepRepository.findById(id);

        if (stepToChange.isPresent()) {
            Step _step = stepToChange.get();
            _step.setTitle(step.getTitle());
            if (!_step.getDescription().isEmpty()) _step.setDescription(step.getDescription());
            return new ResponseEntity<>(stepRepository.save(_step), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            stepRepository.deleteById(id);
            System.out.println("deleted?");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
