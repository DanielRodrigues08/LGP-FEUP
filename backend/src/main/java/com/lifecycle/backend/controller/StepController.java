package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.User;
import com.lifecycle.backend.payload.request.StepRequest;
import com.lifecycle.backend.repository.StepRepository;
import com.lifecycle.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/steps")
public class StepController {

    @Autowired
    StepRepository stepRepository;

    @Autowired
    UserRepository userRepository;

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

    /* ---- STEP CRUD ---- */

    // Step Creation
    @PostMapping("/create")
    public ResponseEntity<Object> createStep(@RequestBody StepRequest stepRequest) {
        Optional<User> owner = userRepository.findById(stepRequest.getOwner());
        if (owner.isEmpty()) {
            return new ResponseEntity<>("Owner not found", HttpStatus.NOT_FOUND);
        }

        Optional<User> backup = userRepository.findById(stepRequest.getBackup());
        if (backup.isEmpty()) {
            return new ResponseEntity<>("Backup not found", HttpStatus.NOT_FOUND);
        }

        Step step = new Step(
                stepRequest.getTitle(),
                stepRequest.getDescription(),
                stepRequest.getDeadline(),
                stepRequest.getDuration(),
                owner.get(),
                backup.get()
        );

        try {
            Step _step = stepRepository.save(step);
            return new ResponseEntity<>(_step, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Step> updateStep(@PathVariable("id") long id, @RequestBody StepRequest stepPatch) {
        Optional<Step> stepToChange = stepRepository.findById(id);

        if (stepToChange.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Step _stepToChange = stepToChange.get();

        if (stepPatch.getTitle() != null) _stepToChange.setTitle(stepPatch.getTitle());
        if (stepPatch.getDescription() != null) _stepToChange.setDescription(stepPatch.getDescription());
        if (stepPatch.getDeadline() != null) _stepToChange.setDeadline(stepPatch.getDeadline());
        if (stepPatch.getDuration() != null) _stepToChange.setDuration(stepPatch.getDuration());
        if (stepPatch.getOwner() != null) {
            Optional<User> owner = userRepository.findById(stepPatch.getOwner());
            if (owner.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            _stepToChange.setOwner(owner.get());
        }
        if (stepPatch.getBackup() != null) {
            Optional<User> backup = userRepository.findById(stepPatch.getBackup());
            if (backup.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            _stepToChange.setBackup(backup.get());
        }

        try {
            Step updatedStep = stepRepository.save(_stepToChange);
            return new ResponseEntity<>(updatedStep, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStep(@PathVariable("id") long id) {
        try {
            stepRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
