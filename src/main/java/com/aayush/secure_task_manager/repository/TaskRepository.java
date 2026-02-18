package com.aayush.secure_task_manager.repository;

import com.aayush.secure_task_manager.entity.Task;
import com.aayush.secure_task_manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByUser(User user, Pageable pageable);
}
