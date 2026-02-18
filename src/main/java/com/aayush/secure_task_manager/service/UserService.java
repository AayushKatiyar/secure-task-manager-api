package com.aayush.secure_task_manager.service;

import com.aayush.secure_task_manager.entity.User;
import com.aayush.secure_task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        user.setRole("ROLE_USER"); // default role
        return userRepository.save(user);
    }
}
