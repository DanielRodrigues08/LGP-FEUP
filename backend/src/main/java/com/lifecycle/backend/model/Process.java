package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;

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

    public Process() { }

    public Process(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /*
    regarding some class relationships:
    https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/onetomany
    */
    /* @ManyToMany
    @JoinTable(name = "steps",
            joinColumns = @JoinColumn(name = "process_id"),
            inverseJoinColumns = @JoinColumn(name = "step_id"))
    private List<Step> steps;*/
}