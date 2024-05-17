package com.lifecycle.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "process")

public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description") // *nullable = true* is redundant
    private String description;

    /*
    regarding some class relationships:
    https://jakarta.ee/specifications/persistence/2.2/apidocs/javax/persistence/onetomany
    -> there is no CascadeType option because we do not want steps
    -> to be deleted from the Steps table when a Process is deleted.
    */
    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StepInProcess> stepsInProcess;
}