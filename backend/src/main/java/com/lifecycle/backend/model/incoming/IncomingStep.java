package com.lifecycle.backend.model.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomingStep {
    private Long id;
    private String title;
    private String description;
    private Integer deadline;
    private Integer duration;
    private Long owner;
    private Long backup;
    private List<Integer> dependencies;
}
