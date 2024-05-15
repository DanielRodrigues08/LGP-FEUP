package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "step_id", nullable = false)
    @JsonIgnore
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboardee_id", nullable = false)
    @JsonIgnore
    private Onboardee onboardee;

    @Enumerated(EnumType.STRING) // Add this annotation
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
