package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "step_info")
public class StepInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING) // Add this annotation
    @Column(name = "status", nullable = false)
    private StepInfoStatus status;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "step_in_process_id", nullable = false)
    @JsonBackReference
    private StepInProcess stepInProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboardee_id", nullable = false)
    @JsonBackReference
    private Onboardee onboardee;
}
