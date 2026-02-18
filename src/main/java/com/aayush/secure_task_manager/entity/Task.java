package com.aayush.secure_task_manager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed;

    private LocalDateTime createdAt;

    @JsonBackReference   // 🔁 prevents infinite recursion
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
