package com.lifecycle.backend.dto;

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
}
