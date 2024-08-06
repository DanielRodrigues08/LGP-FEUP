package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "step_in_process")
public class StepInProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "step_id")
    @JsonBackReference
    private Step step;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    @JsonBackReference
    private Process process;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<StepInProcess> dependencies;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stepInProcess", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StepInfo> stepsInfo;

    public StepInProcess(Step step, Process process, List<StepInProcess> dependencies) {
        this.step = step;
        this.process = process;
        this.dependencies = dependencies;
        this.stepsInfo = new ArrayList<>();
    }
}