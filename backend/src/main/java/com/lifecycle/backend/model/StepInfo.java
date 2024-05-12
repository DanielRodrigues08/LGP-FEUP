package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter

@Entity
@Table(name = "step_info")
public class StepInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_info_id")
    private long step_info_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    private Onboardee onboardee;

    @Column(name = "status", nullable = false)
    private StepInfoStatus status;

    @Column(name = "description")
    private String description;

    public StepInfo() {
    }

    public StepInfo(Step step, Onboardee onboardee, StepInfoStatus status, String description) {
        this.step = step;
        this.onboardee = onboardee;
        this.status = status;
        this.description = description;
    }
}
