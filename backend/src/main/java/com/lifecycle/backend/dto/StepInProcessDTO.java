package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.model.StepInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StepInProcessDTO {
    private Long id;
    private Long stepId;
    private Long processId;
    private List<Long> dependencies;
    private List<Long> stepsInfo;

    static public StepInProcessDTO convertToDTO(StepInProcess stepInProcess) {
        StepInProcessDTO dto = new StepInProcessDTO();
        dto.setId(stepInProcess.getId());
        dto.setStepId(stepInProcess.getStep().getId());
        dto.setProcessId(stepInProcess.getProcess().getId());

        List<Long> dependencies = new ArrayList<>();
        for (StepInProcess dependency : stepInProcess.getDependencies()) {
            dependencies.add(dependency.getId());
        }
        dto.setDependencies(dependencies);

        List<Long> stepsInfo = new ArrayList<>();
        for (StepInfo stepInfo : stepInProcess.getStepsInfo()) {
            stepsInfo.add(stepInfo.getId());
        }
        dto.setStepsInfo(stepsInfo);

        return dto;
    }
}
