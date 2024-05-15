package com.lifecycle.backend.model;

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

    @Column(name = "deadline", nullable = false)
    private int deadline; // type might be changed

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StepInProcess> stepsInProcess = new ArrayList<>();
}
