package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.StepInProcess;
import com.lifecycle.backend.model.StepInfo;
import com.lifecycle.backend.model.StepInfoStatus;
import com.lifecycle.backend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class StepViewDTO {
    private Long stepInfoId;
    private Long stepInProcessId;

    private String stepTitle;
    private String stepDescription;
    private List<Long> dependencies;

    private String ownerName;
    private Long ownerId;
    private String backupName;
    private Long backupId;

    private StepInfoStatus stepInfoStatus;
    private String stepInfodDescription;

    static public StepViewDTO convertToDTO(StepInfo stepInfo) {
        StepViewDTO dto = new StepViewDTO();
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

        User owner = stepInfo.getStepInProcess().getStep().getOwner();
        User backup = stepInfo.getStepInProcess().getStep().getBackup();

        dto.setOwnerName(owner.getName());
        dto.setOwnerId(owner.getId());

        if (backup != null) {
            dto.setBackupName(backup.getName());
            dto.setBackupId(backup.getId());
        }

        return dto;
    }
}
