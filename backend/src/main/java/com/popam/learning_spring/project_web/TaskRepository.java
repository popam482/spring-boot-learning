package com.popam.learning_spring.project_web;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserUsername(String username);

    List<Task> findTasksByCompletedAndUserUsername(Boolean completed, String username);
}