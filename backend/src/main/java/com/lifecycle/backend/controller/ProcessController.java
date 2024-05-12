package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.repository.ProcessRepository;

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

    /* ---- PROCESS CRUD ---- */

    // Process Creation
    /*@PostMapping("/create")
    public ResponseEntity<Process> createProcess(@RequestBody Process process, @RequestBody List<Long> step_ids) {

        List<Step> stepList = new ArrayList<>();
        for (Long id : step_ids) {
            Optional<Step> step = stepRepository.findById(id);
            step.ifPresent(stepList::add);
        }
        process.setSteps(stepList);
        processRepository.save(process);
        return new ResponseEntity<>(process, HttpStatus.OK);
    }*/

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

    @PutMapping("/{id}/steps")
    public ResponseEntity<Process> updateStepsInProcess(@PathVariable("id") long id, @RequestBody List<Long> stepIds) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Process _process = process.get();

        /* Set<Long> stepIdsSet = new HashSet<>(stepIds);
        List<Long> uniqueStepIds = new ArrayList<>(stepIdsSet); // get new step list to update */

        // update process's step list with incoming step list
        for (int i = 0; i < stepIds.size(); i++) {
            Long currentStepId = stepIds.get(i);
            Optional<Step> step = stepRepository.findById(currentStepId);
            if (step.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Step currentStep = step.get(); // get current step from database

            // if incoming step is already in process, update it to new position
            StepInProcess stepToUpdate = _process.getStepInProcessByID(currentStepId);
            if (stepToUpdate != null) {
                System.out.println("Found pre-existing step in process step list: Step ID nº" + currentStepId +
                        ", previous position: " + stepToUpdate.getPosition()
                        + ", new position: " + i);
                stepToUpdate.setPosition(i); // update cascades
            }
            // if not, add new step to the process's list
            else {
                StepInProcess stepToAdd = new StepInProcess(currentStep, _process, i);
                _process.getSteps().add(stepToAdd);
            }
        }

        processRepository.save(_process);
        return new ResponseEntity<>(_process, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/steps")
    public ResponseEntity<Process> removeStepFromProcess(@PathVariable("id") long id, @RequestBody List<Long> stepIds) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<Long> stepIdsSet = new HashSet<>(stepIds);
        List<Long> uniqueStepIds = new ArrayList<>(stepIdsSet);

        Process _process = process.get();
        List<StepInProcess> stepsToRemove = _process.getSteps().stream().filter(stepInProcess -> uniqueStepIds.contains(stepInProcess.getStep().getStep_id())).toList();

        if (stepsToRemove.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        _process.getSteps().removeAll(stepsToRemove);
        processRepository.save(_process);
        return new ResponseEntity<>(_process, HttpStatus.OK);
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
