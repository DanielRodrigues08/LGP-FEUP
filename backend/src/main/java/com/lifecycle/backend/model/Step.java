package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int deadline; // days

    @Column(name="duration")
    private int duration; // days

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false)
    @JsonIgnore
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backup", referencedColumnName = "user_id")
    @JsonIgnore
    private User backup;

    @OneToMany(mappedBy="step", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StepInProcess> processes = new ArrayList<>();

    public Step() { }

    public Step(String title, String description, int deadline, int duration, User owner, User backup) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.duration = duration;
        this.owner = owner;
        this.backup = backup;
    }
}
