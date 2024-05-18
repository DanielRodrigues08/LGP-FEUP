package com.lifecycle.backend.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepRequest {

    private String title;
    private String description;
    private Integer deadline;
    private Integer duration;
    private Long owner;
    private Long backup;
}

