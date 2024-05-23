package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.User;
import com.lifecycle.backend.dto.StepDTO;
import com.lifecycle.backend.repository.StepRepository;
import com.lifecycle.backend.repository.UserRepository;
import com.lifecycle.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/steps")
@Secured({"EMPLOYEE", "HR", "ADMIN"})
public class StepController {

    @Autowired
    StepRepository stepRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<Object> getAllSteps(@RequestParam(required = false) String title) {
        try {
            List<Step> steps = new ArrayList<>();

            if (title == null)
                stepRepository.findAll().forEach(step -> {
                    if (userService.checkStepOwnership(step)) steps.add(step);
                });
            else
                stepRepository.findByTitle(title).forEach(step -> {
                    if (userService.checkStepOwnership(step)) steps.add(step);
                });

            if (steps.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<StepDTO> stepsDTO = new ArrayList<>();
            for (Step step : steps) {
                stepsDTO.add(StepDTO.convertToDTO(step));
            }

            return new ResponseEntity<>(stepsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStepById(@PathVariable("id") long id) {
        Optional<Step> stepData = stepRepository.findById(id);
        if (stepData.isPresent() && !userService.checkStepOwnership(stepData.get())) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (stepData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            StepDTO stepDTO = StepDTO.convertToDTO(stepData.get());
            return new ResponseEntity<>(stepDTO, HttpStatus.OK);
        }
    }


    @PostMapping("/create")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<Object> createStep(@RequestBody StepDTO stepDTO) {
        User owner;
        User backup = null;

        if (stepDTO.getOwner() == null) {
            return new ResponseEntity<>("Owner is required", HttpStatus.BAD_REQUEST);
        }

        Optional<User> _owner = userRepository.findById(stepDTO.getOwner());
        if (_owner.isEmpty()) {
            return new ResponseEntity<>("Owner not found", HttpStatus.NOT_FOUND);
        }
        owner = _owner.get();

        if (stepDTO.getBackup() != null) {
            Optional<User> _backup = userRepository.findById(stepDTO.getBackup());
            if (_backup.isEmpty()) {
                return new ResponseEntity<>("Backup not found", HttpStatus.NOT_FOUND);
            }
            backup = _backup.get();
        }

        Step step = new Step(
                stepDTO.getTitle(),
                stepDTO.getDescription(),
                stepDTO.getDeadline(),
                stepDTO.getDuration(),
                owner,
                backup
        );

        try {
            Step _step = stepRepository.save(step);
            return new ResponseEntity<>(StepDTO.convertToDTO(_step), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<Object> updateStep(@PathVariable("id") long id, @RequestBody StepDTO stepPatch) {
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
            return new ResponseEntity<>(StepDTO.convertToDTO(updatedStep), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<HttpStatus> deleteStep(@PathVariable("id") long id) {
        try {
            stepRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
