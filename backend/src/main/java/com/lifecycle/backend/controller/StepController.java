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

        return stepData.map(step -> new ResponseEntity<>(step, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/steps/test")
    public ResponseEntity<Step> testStep() {
        Step step = new Step("testStep", null, 1);
        System.out.println(step);
        stepRepository.save(step);
        System.out.println("Step added to the database. Check it!");

        return new ResponseEntity<>(step, HttpStatus.OK);
    }

    @GetMapping("/steps/create")
    public ResponseEntity<Step> getStepCreationForm() {
        return null;
    }

    @PostMapping("/steps/create")
    public ResponseEntity<Step> createStep(@RequestBody Step step) {
        stepRepository.save(step);
        return new ResponseEntity<>(step, HttpStatus.OK);
    }

    @PutMapping("/steps/update/{id}")
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

    @DeleteMapping("/steps/delete/{id}")
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
