package com.lifecycle.backend.controller;

import com.lifecycle.backend.model.HRMember;
import com.lifecycle.backend.model.HRMemberRole;
import com.lifecycle.backend.repository.HRMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hrmembers")
public class HRMemberController {

    @Autowired
    private HRMemberRepository hrmemberRepository;

    // GET all HR members
    @GetMapping
    public ResponseEntity<List<HRMember>> getAllHRMembers() {
        List<HRMember> hrMembers = hrmemberRepository.findAll();
        return ResponseEntity.ok(hrMembers);
    }

    // GET HR member by ID
    @GetMapping("/{id}")
    public ResponseEntity<HRMember> getHRMemberById(@PathVariable Long id) {
        Optional<HRMember> hrMember = hrmemberRepository.findById(id);
        return hrMember.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mark HR member attendance
    @PostMapping("/{id}/attendance/mark")
    public ResponseEntity<Void> markAttendance(@PathVariable Long id) {
        Optional<HRMember> hrMemberOptional = hrmemberRepository.findById(id);
        if (hrMemberOptional.isPresent()) {
            HRMember hrMember = hrMemberOptional.get();
            hrMember.markAttendance(); // Mark attendance
            hrmemberRepository.save(hrMember); // Save updated HR member
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mark HR member leave
    @PostMapping("/{id}/leave/mark")
    public ResponseEntity<Void> markLeave(@PathVariable Long id) {
        Optional<HRMember> hrMemberOptional = hrmemberRepository.findById(id);
        if (hrMemberOptional.isPresent()) {
            HRMember hrMember = hrMemberOptional.get();
            hrMember.markLeave(); // Mark leave
            hrmemberRepository.save(hrMember); // Save updated HR member
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Reset HR member attendance
    @PostMapping("/{id}/attendance/reset")
    public ResponseEntity<Void> resetAttendance(@PathVariable Long id) {
        Optional<HRMember> hrMemberOptional = hrmemberRepository.findById(id);
        if (hrMemberOptional.isPresent()) {
            HRMember hrMember = hrMemberOptional.get();
            hrMember.resetAttendance(); // Reset attendance
            hrmemberRepository.save(hrMember); // Save updated HR member
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Test endpoint to create and save a dummy HR member to the database
    @GetMapping("/test/hrmember")
    public ResponseEntity<HRMember> testHRMember() {
        HRMember hrMember = new HRMember("testHRMember", "password123", "hrName", "999888777", "hr@gmail.com", HRMemberRole.ADMIN);
        System.out.println(hrMember);
        hrmemberRepository.save(hrMember);
        System.out.println("HRMember added to the database. Check it!");

        return new ResponseEntity<>(hrMember, HttpStatus.OK);
    }

    // POST create HR member
    @PostMapping
    public ResponseEntity<HRMember> createHRMember(@RequestBody HRMember hrMember) {
        HRMember savedHRMember = hrmemberRepository.save(hrMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHRMember);
    }

    // PUT update HR member
    @PutMapping("/{id}")
    public ResponseEntity<HRMember> updateHRMember(@PathVariable Long id, @RequestBody HRMember hrMember) {
        if (!hrmemberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hrMember.setId(id); // Ensure ID is set for the update operation
        HRMember updatedHRMember = hrmemberRepository.save(hrMember);
        return ResponseEntity.ok(updatedHRMember);
    }

    // DELETE HR member by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHRMember(@PathVariable Long id) {
        if (!hrmemberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        hrmemberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}