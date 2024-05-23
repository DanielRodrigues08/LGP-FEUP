package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.Step;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepDTO {
    private Long id;
    private String title;
    private String description;
    private Integer deadline;
    private Integer duration;
    private Long owner;
    private Long backup;

    static public StepDTO convertToDTO(Step step) {
        StepDTO stepDTO = new StepDTO();
        stepDTO.setId(step.getId());
        stepDTO.setTitle(step.getTitle());
        stepDTO.setDescription(step.getDescription());
        stepDTO.setDeadline(step.getDeadline());
        stepDTO.setDuration(step.getDuration());
        stepDTO.setOwner(step.getOwner().getId());
        stepDTO.setBackup(step.getBackup().getId());
        return stepDTO;
    }
}

