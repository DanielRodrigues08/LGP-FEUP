package com.lifecycle.backend.model;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hr_members")
public class HRMember extends User {

    @Column(name = "role", nullable = false)
    private HRMemberRole role;

    // Constructor for HRMember class
    public HRMember(String username, String password, HRMemberRole role) {
        super(username, password);
        this.role = role;
    }

    // Implementation of getRole method
    public HRMemberRole getRole() {
        return this.role;
    }
}

