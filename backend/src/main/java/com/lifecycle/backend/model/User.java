package com.lifecycle.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING) // Specify that permissionLevel is an Enum
    @Column(name = "permission_level")
    private UserPermission permissionLevel;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<Step> ownerSteps;

    @OneToMany(mappedBy = "backup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<Step> backupSteps;

    // Constructor for User class
    public User(String email, String password, String name, String phoneNumber, UserPermission permissionLevel) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.permissionLevel = permissionLevel;
    }

}
