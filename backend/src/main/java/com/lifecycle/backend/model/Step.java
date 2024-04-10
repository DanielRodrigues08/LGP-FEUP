package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    // relationship with processes
    @ManyToMany(mappedBy = "steps")
    private List<Process> processes;

}