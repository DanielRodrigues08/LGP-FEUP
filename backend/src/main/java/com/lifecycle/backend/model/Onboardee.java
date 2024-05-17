package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "onboardee")
public class Onboardee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Additional fields for Onboardee
    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    // New attributes for Onboardee
    @Enumerated(EnumType.STRING) // Add this annotation
    @Column(name = "state")
    private OnboardeeStatus state;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "annual_salary")
    private String annualSalary;

    @Column(name = "payroll_number")
    private String payrollNumber;

    @Column(name = "start_date")
    private LocalDate startDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onboardee")
    @JsonManagedReference
    private List<StepInfo> stepsInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Process activeProcess;
}

