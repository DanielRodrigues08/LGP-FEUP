package com.lifecycle.backend.model;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hr_members")
public class HRMember extends User {

    // Additional field for HRMember
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private HRMemberRole role;

    @Column(name = "attendance")
    private boolean attendance;

    // Constructor for HRMember class
    public HRMember(String username, String password, String name, String phoneNumber, String email, HRMemberRole role) {
        super(username, password, name, phoneNumber, email);
        this.role = role;
        this.attendance = false; // Default attendance is false
    }

    // Methods to mark attendance, leave, and reset attendance
    public void markAttendance() {
        this.attendance = true;
    }

    public void markLeave() {
        this.attendance = false;
    }

    public void resetAttendance() {
        this.attendance = false;
    }
}