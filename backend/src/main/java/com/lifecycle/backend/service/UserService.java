package com.lifecycle.backend.service;

import com.lifecycle.backend.model.Step;
import com.lifecycle.backend.model.User;
import com.lifecycle.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkStepOwnership(Step step) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var roles = authentication.getAuthorities();
        boolean hasReadRole = roles.stream()
                .anyMatch(authority -> authority.getAuthority().equals("EMPLOYEE"));
        if (!hasReadRole) return true;

        var loggedUser = userRepository.findByEmail(authentication.getName());
        return loggedUser.isPresent() &&
                Objects.equals(loggedUser.get().getId(), step.getOwner().getId());
    }

    public Optional<User> getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains("ROLE_ANONYMOUS")) {
            return Optional.empty();
        }
        return userRepository.findByEmail(authentication.getName());
    }

}
