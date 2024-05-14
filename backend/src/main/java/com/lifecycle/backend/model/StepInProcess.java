package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "steps_in_process")
public class StepInProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "steps_in_process_id")
    private long step_in_process_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    @JsonIgnore
    private Process process;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Step> dependencies;

    public StepInProcess() {}

    public StepInProcess(Step step, Process process, List<Step> dependencies) {
        this.step = step;
        this.process = process;
        this.dependencies = dependencies;
    }
}