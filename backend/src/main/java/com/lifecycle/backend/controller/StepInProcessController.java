package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class StepInProcessController {
    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private StepInProcessRepository stepInProcessRepository;

    @GetMapping("/processes/{processId}/steps/{stepId}")
    public ResponseEntity<Object> getStepInProcessDependecies(@PathVariable("processId") long processId, @PathVariable("stepId") long stepId) {
        Optional<Process> process = processRepository.findById(processId);
        if (process.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Step> step = stepRepository.findById(stepId);
        if (step.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<StepInProcess> stepInProcess = stepInProcessRepository.findByProcessAndStep(process.get(), step.get());
        if (stepInProcess.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Long> dependencies = new ArrayList<>();
        for (Step dependency : stepInProcess.get().getDependencies()) {
            dependencies.add(dependency.getStep_id());
        }

        return new ResponseEntity<>(Map.of(
                "step_id", stepInProcess.get().getStep().getStep_id(),
                "dependencies", dependencies
        ), HttpStatus.OK);
    }

    @GetMapping("/processes/{id}/steps")
    public ResponseEntity<Object> getStepsInProcess(@PathVariable("id") long id) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Long> steps = new ArrayList<>();
        for (StepInProcess stepInProcess : process.get().getSteps()) {
            steps.add(stepInProcess.getStep().getStep_id());
        }

        return new ResponseEntity<>(Map.of(
                "process_id", process.get().getProcess_id(),
                "steps", steps
        ), HttpStatus.OK);
    }


    @PatchMapping("/processes/{id}/steps")
    public ResponseEntity<Object> updateStepInProcess(@PathVariable("id") long id, @RequestBody Map<String, Object> patch) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Process _process = process.get();
        try {
            long stepId = ((Number) patch.get("step_id")).longValue();
            Optional<Step> step = stepRepository.findById(stepId);
            if (step.isEmpty()) {
                throw new IllegalArgumentException("Invalid step id");
            }

            Step _step = step.get();
            StepInProcess _stepInProcess = stepInProcessRepository.findByProcessAndStep(_process, _step).orElse(null);

            switch ((String) patch.get("type")) {
                case "dependency":
                    long dependencyId = (long) patch.get("dependency_id");
                    Optional<Step> dependency = stepRepository.findById(dependencyId);

                    if (dependency.isEmpty() || _stepInProcess == null) {
                        throw new IllegalArgumentException("Invalid dependency id");
                    }

                    Step _dependency = dependency.get();

                    if (patch.get("op").equals("add")) {
                        if (!_stepInProcess.getDependencies().contains(_dependency)) {
                            _stepInProcess.getDependencies().add(_dependency);
                            stepInProcessRepository.save(_stepInProcess);
                        }
                    } else if (patch.get("op").equals("remove")) {
                        _stepInProcess.getDependencies().remove(_dependency);
                        stepInProcessRepository.save(_stepInProcess);
                    } else {
                        throw new IllegalArgumentException("Invalid operation type");
                    }
                    break;
                case "step":
                    if (patch.get("op").equals("add")) {
                        if (_stepInProcess == null) {
                            _process.getSteps().add(new StepInProcess(_step, _process, new ArrayList<>()));
                            processRepository.save(_process);
                        }
                    } else if (patch.get("op").equals("remove")) {
                        if (_stepInProcess != null) {
                            stepInProcessRepository.delete(_stepInProcess);
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid operation type");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
