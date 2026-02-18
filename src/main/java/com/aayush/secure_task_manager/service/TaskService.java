package com.aayush.secure_task_manager.service;

import com.aayush.secure_task_manager.entity.Task;
import com.aayush.secure_task_manager.entity.User;
import com.aayush.secure_task_manager.repository.TaskRepository;
import com.aayush.secure_task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public Page<Task> getMyTasks(Pageable pageable) {

        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUser(user, pageable);
    }

    public Task updateTask(Long taskId, Task updatedTask) {

        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔐 Ensure user owns the task
        if (!existingTask.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this task");
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(existingTask);
    }
    public void deleteTask(Long taskId) {

        String email = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔐 Ensure ownership
        if (!existingTask.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this task");
        }

        taskRepository.delete(existingTask);
    }


}
