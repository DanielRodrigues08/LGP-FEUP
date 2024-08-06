package com.lifecycle.backend.service;

import com.lifecycle.backend.dto.JobSchedulingDTO;
import com.lifecycle.backend.model.StepInfo;
import com.lifecycle.backend.model.StepInfoStatus;
import com.lifecycle.backend.repository.StepInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StepInfoService {
    @Autowired
    private StepInfoRepository stepInfoRepository;

    public List<JobSchedulingDTO> getJobScheduling() {
        List<JobSchedulingDTO> stepInfoDTOs = new ArrayList<>();
        for (StepInfo step : stepInfoRepository.findAll()) {
            if (step.getStatus() == StepInfoStatus.ABORTED || step.getStatus() == StepInfoStatus.COMPLETED) {
                continue;
            }
            stepInfoDTOs.add(JobSchedulingDTO.convertToDto(step));
        }

        stepInfoDTOs.sort(Comparator.comparing(JobSchedulingDTO::getDueDate));

        return stepInfoDTOs;
    }
}
