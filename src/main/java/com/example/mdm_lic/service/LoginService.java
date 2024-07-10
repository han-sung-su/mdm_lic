package com.example.mdm_lic.service;

import com.example.mdm_lic.model.entity.User;
import com.example.mdm_lic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String username, String password) {
        logger.info("Attempting to authenticate user: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("User not found: " + username);
            return false;
        }
        logger.info("Found user: " + username);
        logger.info("Stored password hash: " + user.getPassword());
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        logger.info("Password match result: " + matches);
        return matches;
    }
}