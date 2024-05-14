package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.User;
import com.lifecycle.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.lifecycle.backend.model.UserPermission.ADMIN;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        // Create new user's account
        User newUser = new User(user.getEmail(),
                encoder.encode(user.getPassword()),
                user.getName(),
                user.getPhoneNumber(),
                user.getPermissionLevel()
        );

        User savedUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // PUT update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id); // Ensure ID is set for the update operation
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

