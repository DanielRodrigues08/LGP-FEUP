package com.lifecycle.backend.dto;

import com.lifecycle.backend.model.Process;
import com.lifecycle.backend.model.StepInProcess;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProcessDTO {
    private Long id;
    private String title;
    private String description;
    private List<Integer> stepsInProcess;

    static public ProcessDTO convertToDTO(Process process) {
        ProcessDTO processDTO = new ProcessDTO();
        processDTO.setId(process.getId());
        processDTO.setTitle(process.getTitle());
        processDTO.setDescription(process.getDescription());

        List<Integer> stepsInProcess = new ArrayList<>();
        for (StepInProcess stepInProcess : process.getStepsInProcess()) {
            stepsInProcess.add((int) stepInProcess.getStep().getId());
        }
        processDTO.setStepsInProcess(stepsInProcess);

        return processDTO;
    }
}
