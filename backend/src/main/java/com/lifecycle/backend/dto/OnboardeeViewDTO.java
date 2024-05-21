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
public class OnboardeeViewDTO {
    private Long idProcess;
    private String processTitle;
    private List<StepViewDTO> steps;

    static public OnboardeeViewDTO convertToDTO(Onboardee onboardee) {
        OnboardeeViewDTO dto = new OnboardeeViewDTO();
        dto.setIdProcess(onboardee.getActiveProcess().getId());
        dto.setProcessTitle(onboardee.getActiveProcess().getTitle());

        List<StepViewDTO> steps = new ArrayList<>();
        for (StepInfo stepInfo : onboardee.getStepsInfo()) {
            if (stepInfo.getStatus() != StepInfoStatus.ABORTED) {
                steps.add(StepViewDTO.convertToDTO(stepInfo));
            }
        }
        dto.setSteps(steps);
        return dto;
    }
}

