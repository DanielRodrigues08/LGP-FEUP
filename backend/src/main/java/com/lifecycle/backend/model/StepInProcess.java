package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;

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
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    private Process process;

    @Column(name = "position", nullable = false)
    private int position;

    public StepInProcess() {}

    public StepInProcess(Step step, Process process, int position) {
        this.step = step;
        this.process = process;
        this.position = position;
    }
}