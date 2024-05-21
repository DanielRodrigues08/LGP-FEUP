package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.model.StepInfo;
import com.lifecycle.backend.model.StepInfoStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class OnboardeeStepDTO {
    private Long stepInfoId;
    private Long stepInProcessId;

    private String stepTitle;
    private String stepDescription;
    private List<Long> dependencies;

    private StepInfoStatus stepInfoStatus;
    private String stepInfodDescription;

    static public OnboardeeStepDTO convertToDTO(StepInfo stepInfo) {
        OnboardeeStepDTO dto = new OnboardeeStepDTO();
        dto.setStepInfoId(stepInfo.getId());
        dto.setStepInProcessId(stepInfo.getStepInProcess().getId());
        dto.setStepTitle(stepInfo.getStepInProcess().getStep().getTitle());
        dto.setStepDescription(stepInfo.getStepInProcess().getStep().getDescription());

        List<Long> dependencies = new ArrayList<>();
        for (StepInProcess stepInProcess : stepInfo.getStepInProcess().getDependencies()) {
            dependencies.add(stepInProcess.getId());
        }

        dto.setDependencies(dependencies);
        dto.setStepInfoStatus(stepInfo.getStatus());
        dto.setStepInfodDescription(stepInfo.getDescription());

        return dto;
    }
}
