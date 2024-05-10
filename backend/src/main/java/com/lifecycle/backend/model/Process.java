package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "processes")

public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private long process_id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name = "description") // *nullable = true* is redundant
    private String description;

    /*
    regarding some class relationships:
    https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/onetomany
    -> there is no CascadeType option because we do not want steps
    -> to be deleted from the Steps table when a Process is deleted.
    */
    @OneToMany(mappedBy="process", cascade = CascadeType.ALL)
    private List<StepInProcess> steps;

    @OneToMany(mappedBy="process", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Onboardee> onboardees;

    public Process() { }

    public Process(String title, String description) {
        this.title = title;
        this.description = description;
        // this.steps = new ArrayList<>();
    }

    public StepInProcess getStepInProcessByID(Long id) {
        return this.steps.stream()
                .filter(step -> id.equals(step.getStep().getStep_id()))
                .findAny()
                .orElse(null);
    }

}