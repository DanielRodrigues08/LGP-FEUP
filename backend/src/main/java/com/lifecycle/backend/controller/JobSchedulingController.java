package com.lifecycle.backend.controller;

import com.lifecycle.backend.service.StepInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/job-scheduling")
public class JobSchedulingController {

    @Autowired
    private StepInfoService stepInfoService;

    @GetMapping("")
    public ResponseEntity<Object> getJobScheduling() {
        return ResponseEntity.ok(stepInfoService.getJobScheduling());
    }
}
