package com.lifecycle.backend.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OnboardeeRequest {

    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    private String nationality;
    private String annualSalary;
    private String payrollNumber;
    private LocalDate startDate;
}

