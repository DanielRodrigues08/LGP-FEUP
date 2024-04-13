package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private long step_id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name = "description") // *nullable = true* is redundant
    private String description;

    @Column(name="deadline", nullable = false)
    private int deadline; // type might be changed

    // relationship with 'processes' is a unidirectional ManyToMany with 'processes' as the owner
    // https://www.baeldung.com/hibernate-many-to-many

    public Step() { }

    public Step(String title, String description, int deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
}