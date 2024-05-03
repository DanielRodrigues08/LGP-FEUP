package com.lifecycle.backend.service;

import com.lifecycle.backend.model.User;
import com.lifecycle.backend.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AttendanceResetService {

    private final UserRepository userRepository;

    public AttendanceResetService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Scheduled task to reset attendance daily at 23:59
    @Scheduled(cron = "0 59 23 * * ?")
    public void resetAttendanceDaily() {
        // Get all HR members and reset their attendance
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            user.resetAttendance();
            userRepository.save(user);
        }
    }
}

