package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lifecycle.backend.model.incoming.IncomingStep;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "step")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description") // *nullable = true* is redundant
    private String description;

    @Column(name="deadline", nullable = false)
    private Integer deadline; // days

    @Column(name="duration")
    private Integer duration; // days

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "user_id", nullable = false)
    @JsonBackReference
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backup", referencedColumnName = "user_id")
    @JsonBackReference
    private User backup;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StepInProcess> stepsInProcess = new ArrayList<>();

    public Step(String title, String description, Integer deadline, Integer duration, User owner, User backup) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.duration = duration;
        this.owner = owner;
        this.backup = backup;
    }

    public Step(IncomingStep incomingStep, User owner, User backup) {
        this.title = incomingStep.getTitle();
        this.description = incomingStep.getDescription();
        this.deadline = incomingStep.getDeadline();
        this.duration = incomingStep.getDuration();
        this.owner = owner;
        this.backup = backup;
    }
}
