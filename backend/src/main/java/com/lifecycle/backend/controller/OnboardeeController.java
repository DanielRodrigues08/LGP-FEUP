package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.repository.OnboardeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/onboardees")
public class OnboardeeController {

    @Autowired
    private OnboardeeRepository onboardeeRepository;

    // GET all onboardees
    @GetMapping
    public ResponseEntity<List<Onboardee>> getAllOnboardees() {
        List<Onboardee> onboardees = onboardeeRepository.findAll();
        return ResponseEntity.ok(onboardees);
    }
    // POST create onboardee
    @PostMapping
    public ResponseEntity<Onboardee> createOnboardee(@RequestBody Onboardee onboardee) {
        Onboardee savedOnboardee = onboardeeRepository.save(onboardee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOnboardee);
    }

    // GET onboardee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Onboardee> getOnboardeeById(@PathVariable Long id) {
        Optional<Onboardee> onboardee = onboardeeRepository.findById(id);
        return onboardee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Test endpoint to create and save a dummy user to the database
    @GetMapping("/test/onboardee")
    public ResponseEntity<Onboardee> testOnboardee() {
        Onboardee onboardee = new Onboardee("testOnboardee", "password123", "onboardeeName", "999888666", "onboardee@gmail.com");
        System.out.println(onboardee);
        onboardeeRepository.save(onboardee);
        System.out.println("Onboardee added to the database. Check it!");

        return new ResponseEntity<>(onboardee, HttpStatus.OK);
    }

    // PUT update onboardee
    @PutMapping("/{id}")
    public ResponseEntity<Onboardee> updateOnboardee(@PathVariable Long id, @RequestBody Onboardee onboardee) {
        if (!onboardeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        onboardee.setId(id); // Ensure ID is set for the update operation
        Onboardee updatedOnboardee = onboardeeRepository.save(onboardee);
        return ResponseEntity.ok(updatedOnboardee);
    }

    // DELETE onboardee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnboardee(@PathVariable Long id) {
        if (!onboardeeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        onboardeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
