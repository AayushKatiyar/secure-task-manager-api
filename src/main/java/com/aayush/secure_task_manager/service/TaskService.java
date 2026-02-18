package com.aayush.secure_task_manager.service;

import com.aayush.secure_task_manager.entity.Task;
import com.aayush.secure_task_manager.entity.User;
import com.aayush.secure_task_manager.repository.TaskRepository;
import com.aayush.secure_task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(Task task) {

        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);

        return taskRepository.save(task);
    }

    public List<Task> getMyTasks() {

        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user);
    }
}
