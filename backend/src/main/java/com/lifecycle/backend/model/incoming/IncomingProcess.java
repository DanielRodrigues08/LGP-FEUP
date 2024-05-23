package com.lifecycle.backend.model.incoming;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lifecycle.backend.model.Process;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingProcess {
    private String title;
    private String description;

    @JsonBackReference
    private List<IncomingStep> incomingSteps;
}