package com.lifecycle.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.OnboardeeStatus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OnboardeeDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    private String nationality;
    private String annualSalary;
    private String payrollNumber;
    private LocalDate startDate;
    private OnboardeeStatus state;
    private Long processId;

    static public OnboardeeDTO convertToDTO(Onboardee onboardee) {
        OnboardeeDTO dto = new OnboardeeDTO();
        dto.setId(onboardee.getId());
        dto.setName(onboardee.getName());
        dto.setPhoneNumber(onboardee.getPhoneNumber());
        dto.setEmail(onboardee.getEmail());
        dto.setGender(onboardee.getGender());
        dto.setNationality(onboardee.getNationality());
        dto.setAnnualSalary(onboardee.getAnnualSalary());
        dto.setPayrollNumber(onboardee.getPayrollNumber());
        dto.setStartDate(onboardee.getStartDate());
        dto.setState(onboardee.getState());

        if (onboardee.getActiveProcess() != null)
            dto.setProcessId(onboardee.getActiveProcess().getId());

        return dto;
    }

    @JsonIgnore
    public boolean isNameEmpty() {
        return this.name == null || this.name.trim().trim().isEmpty();
    }

    @JsonIgnore
    public boolean isPhoneNumberEmpty() {
        return this.phoneNumber == null || this.phoneNumber.trim().isEmpty();
    }

    @JsonIgnore
    public boolean isEmailEmpty() {
        return this.email == null || this.email.trim().isEmpty();
    }

    @JsonIgnore
    public boolean isGenderEmpty() {
        return this.gender == null || this.gender.trim().isEmpty();
    }

    @JsonIgnore
    public boolean isNationalityEmpty() {
        return this.gender == null || this.nationality.trim().isEmpty();
    }

    @JsonIgnore
    public boolean isAnnualSalaryEmpty() {
        return this.annualSalary == null || this.annualSalary.trim().isEmpty();
    }

    @JsonIgnore
    public boolean isPayrollNumberEmpty() {
        return this.payrollNumber == null || this.payrollNumber.trim().isEmpty();
    }

    @JsonIgnore
    public boolean isStartDateEmpty() {
        return this.startDate == null;
    }

    @JsonIgnore
    public boolean isStateEmpty() {
        return this.state == null;
    }

    @JsonIgnore
    public boolean isProcessIdEmpty() {
        return this.processId == null;
    }
}
