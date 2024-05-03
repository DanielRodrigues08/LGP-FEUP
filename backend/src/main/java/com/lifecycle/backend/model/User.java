package com.lifecycle.backend.model;

import lombok.*;

import jakarta.persistence.*;

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

    @Column(name = "permission_level")
    private UserPermission permissionLevel;

    @Column(name = "attendance")
    private Boolean attendance;

    // Constructor for User class
    public User(String email, String password, String name, String phoneNumber, UserPermission permissionLevel) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.permissionLevel = permissionLevel;
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
