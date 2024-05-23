package com.lifecycle.backend.service;

import com.lifecycle.backend.exception.ProcessAlreadyAssignedException;
import com.lifecycle.backend.model.*;
import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.repository.OnboardeeRepository;
import com.lifecycle.backend.repository.ProcessRepository;
import com.lifecycle.backend.repository.StepInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnboardeeService {

    @Autowired
    private OnboardeeRepository onboardeeRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private StepInfoRepository stepInfoRepository;

    public Onboardee updateOnboardeeProcess(Long onboardeeId, Long processId) throws Exception {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(onboardeeId);
        if (onboardee.isEmpty()) {
            throw new Exception("Onboardee not found");
        }

        Onboardee _onboardee = onboardee.get();

        if (_onboardee.getActiveProcess() != null && _onboardee.getActiveProcess().getId() == processId) {
            throw new ProcessAlreadyAssignedException("Onboardee already has this process");
        }

        Optional<Process> process = processRepository.findById(processId);

        if (process.isEmpty()) {
            throw new Exception("Process not found");
        }

        Process _process = process.get();

        boolean addNewStepsInfo = true;

        for (StepInfo stepInfo : _onboardee.getStepsInfo()) {
            if (stepInfo.getStepInProcess().getProcess().getId() == processId) {
                stepInfo.setStatus(StepInfoStatus.NOT_STARTED);
                addNewStepsInfo = false;
            } else {
                stepInfo.setStatus(StepInfoStatus.ABORTED);
            }
        }

        if (addNewStepsInfo) {
            for (StepInProcess stepInProcess : _process.getStepsInProcess()) {
                StepInfo stepInfo = new StepInfo();
                stepInfo.setOnboardee(_onboardee);
                stepInfo.setStepInProcess(stepInProcess);
                stepInfo.setStatus(StepInfoStatus.NOT_STARTED);
                stepInfoRepository.save(stepInfo);
                _onboardee.getStepsInfo().add(stepInfo);
            }
        }
        _onboardee.setActiveProcess(_process);
        onboardeeRepository.save(_onboardee);

        return _onboardee;
    }

}
