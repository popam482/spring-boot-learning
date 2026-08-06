package com.popam.learning_spring.project_web;

public class TaskHasNoPriority extends RuntimeException {
    public TaskHasNoPriority() {
        super("Task has no priority");
    }
}
