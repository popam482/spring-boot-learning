package com.popam.learning_spring.project_web;

public class TaskCompleted extends RuntimeException {
    public TaskCompleted(Integer id) {
        super("Task " + id + " is completed and cannot be deleted");
    }
}
