package com.lab1.backend.service;

import com.lab1.backend.dto.RegisterRequest;
import com.lab1.backend.entity.User;
import com.lab1.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.lab1.backend.dto.LoginRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // We use this to encrypt passwords so they aren't plain text in the DB
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(RegisterRequest request) {
        // 1. Check if email exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // 2. Create new User entity
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setIdNumber(request.getIdNumber());
        newUser.setEmail(request.getEmail());
        newUser.setUserType(request.getUserType());

        // 3. Encrypt the password before saving!
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        // 4. Save to Database
        return userRepository.save(newUser);
    }

    public User loginUser(LoginRequest request) {
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // 2. Check if the password matches the hash in the DB
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }

        // 3. Return the user (Success!)
        return user;
    }
}