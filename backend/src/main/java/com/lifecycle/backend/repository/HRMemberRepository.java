package com.lifecycle.backend.repository;

import com.lifecycle.backend.model.HRMember;
import com.lifecycle.backend.model.Onboardee;
import com.lifecycle.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HRMemberRepository extends JpaRepository<HRMember, Long> {
    Optional<HRMember> findByUsername(String username);
    Optional<HRMember> findById(Long id);
}
