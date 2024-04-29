package com.lifecycle.backend.service;

import com.lifecycle.backend.model.HRMember;
import com.lifecycle.backend.repository.HRMemberRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AttendanceResetService {

    private final HRMemberRepository hrMemberRepository;

    public AttendanceResetService(HRMemberRepository hrMemberRepository) {
        this.hrMemberRepository = hrMemberRepository;
    }

    // Scheduled task to reset attendance daily at 23:59
    @Scheduled(cron = "0 59 23 * * ?")
    public void resetAttendanceDaily() {
        // Get all HR members and reset their attendance
        Iterable<HRMember> hrMembers = hrMemberRepository.findAll();
        for (HRMember hrMember : hrMembers) {
            hrMember.resetAttendance();
            hrMemberRepository.save(hrMember);
        }
    }
}

