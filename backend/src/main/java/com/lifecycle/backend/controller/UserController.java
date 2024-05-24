package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.User;
import com.lifecycle.backend.model.UserPermission;
import com.lifecycle.backend.payload.response.MessageResponse;
import com.lifecycle.backend.repository.UserRepository;
import com.lifecycle.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@Secured({"EMPLOYEE", "HR", "ADMIN"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    // GET all users
    @GetMapping
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // GET user by ID
    @GetMapping("/{id}")
    @Secured({"HR", "ADMIN"})
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create user
    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> createUser(@RequestBody User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

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
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        var authUser = userService.getAuthenticatedUser();
        if (authUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var authUserObj = authUser.get();
        if (authUserObj.getId().equals(id) || authUserObj.getPermissionLevel() == UserPermission.ADMIN) {
            Optional<User> existingUserOpt = userRepository.findById(id);
            if (!existingUserOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User existingUser = existingUserOpt.get();

            // Check if the email is being updated to one that's already in use
            if (!existingUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            // Update user details
            existingUser.setEmail(user.getEmail());
            existingUser.setName(user.getName());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setPermissionLevel(user.getPermissionLevel());

            // Update password if it's provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(encoder.encode(user.getPassword()));
            }

            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH update user role
    @PatchMapping("/{id}/role")
    @Secured("ADMIN")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (!existingUserOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = existingUserOpt.get();
        existingUser.setPermissionLevel(user.getPermissionLevel());

        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

}

