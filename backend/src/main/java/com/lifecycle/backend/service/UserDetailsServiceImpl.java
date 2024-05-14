package com.lifecycle.backend.service;

import com.lifecycle.backend.model.MyUserDetails;
import com.lifecycle.backend.model.User;
import com.lifecycle.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
@CrossOrigin(origins = "*")
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        } else if (user == null) {
            throw new UsernameNotFoundException("User is null");
        }

        return MyUserDetails.build(user.orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

}
