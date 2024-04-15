package com.lifecycle.backend.model;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "onboardees")
public class Onboardee extends User {

    // Constructor for Onboardee class
    public Onboardee(String username, String password) {
        super(username, password);
    }
}
