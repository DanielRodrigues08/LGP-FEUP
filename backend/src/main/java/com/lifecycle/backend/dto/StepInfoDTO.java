package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.StepInfoStatus;
import com.lifecycle.backend.model.StepInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepInfoDTO {
    private Long id;
    private StepInfoStatus status;
    private String description;
    private Long stepInProcessId;
    private Long onboardeeId;

    static public StepInfoDTO convertToDTO(StepInfo stepInfo) {
        StepInfoDTO stepInfoDTO = new StepInfoDTO();
        stepInfoDTO.setId(stepInfo.getId());
        stepInfoDTO.setStatus(stepInfo.getStatus());
        stepInfoDTO.setDescription(stepInfo.getDescription());
        stepInfoDTO.setStepInProcessId(stepInfo.getStepInProcess().getId());
        stepInfoDTO.setOnboardeeId(stepInfo.getOnboardee().getId());
        return stepInfoDTO;
    }
}
