package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.StepInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobSchedulingDTO {
    private String processTitle;
    private Long processId;

    private String stepTitle;
    private Long stepId;

    private Long stepInProcessId;
    private Long stepInfoId;

    private String onboardeeName;
    private Long onboardeeId;

    private String ownerName;
    private Long ownerId;

    private String backupName;
    private Long backupId;

    private LocalDate dueDate;

    static public JobSchedulingDTO convertToDto(StepInfo stepInfo) {
        JobSchedulingDTO jobSchedulingDTO = new JobSchedulingDTO();

        jobSchedulingDTO.setProcessTitle(stepInfo.getStepInProcess().getProcess().getTitle());
        jobSchedulingDTO.setProcessId(stepInfo.getStepInProcess().getProcess().getId());

        jobSchedulingDTO.setStepTitle(stepInfo.getStepInProcess().getStep().getTitle());
        jobSchedulingDTO.setStepId(stepInfo.getStepInProcess().getStep().getId());

        jobSchedulingDTO.setStepInProcessId(stepInfo.getStepInProcess().getId());
        jobSchedulingDTO.setStepInfoId(stepInfo.getId());

        jobSchedulingDTO.setOnboardeeName(stepInfo.getOnboardee().getName());
        jobSchedulingDTO.setOnboardeeId(stepInfo.getOnboardee().getId());

        jobSchedulingDTO.setOwnerName(stepInfo.getStepInProcess().getStep().getOwner().getName());
        jobSchedulingDTO.setOwnerId(stepInfo.getStepInProcess().getStep().getOwner().getId());

        if (stepInfo.getStepInProcess().getStep().getBackup() != null) {
            jobSchedulingDTO.setBackupName(stepInfo.getStepInProcess().getStep().getBackup().getName());
            jobSchedulingDTO.setBackupId(stepInfo.getStepInProcess().getStep().getBackup().getId());
        }

        jobSchedulingDTO.setDueDate(stepInfo.getOnboardee().getStartDate().minusDays(stepInfo.getStepInProcess().getStep().getDeadline()));


        return jobSchedulingDTO;
    }
}


