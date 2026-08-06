package com.popam.learning_spring.project_web;

public class TaskNotFound extends RuntimeException {
    public TaskNotFound(Integer id) {
        super("Task with id " + id + " not found");
    }
}
