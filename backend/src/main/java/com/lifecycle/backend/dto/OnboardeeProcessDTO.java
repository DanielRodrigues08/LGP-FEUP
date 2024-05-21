package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.StepInfo;
import com.lifecycle.backend.model.StepInfoStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class OnboardeeProcessDTO {
    private Long idProcess;
    private String processTitle;
    private List<OnboardeeStepDTO> steps;

    static public OnboardeeProcessDTO convertToDTO(Onboardee onboardee) {
        OnboardeeProcessDTO dto = new OnboardeeProcessDTO();
        dto.setIdProcess(onboardee.getActiveProcess().getId());
        dto.setProcessTitle(onboardee.getActiveProcess().getTitle());

        List<OnboardeeStepDTO> steps = new ArrayList<>();
        for (StepInfo stepInfo : onboardee.getStepsInfo()) {
            if (stepInfo.getStatus() != StepInfoStatus.ABORTED) {
                steps.add(OnboardeeStepDTO.convertToDTO(stepInfo));
            }
        }
        dto.setSteps(steps);
        return dto;
    }
}

