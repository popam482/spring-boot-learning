package com.popam.learning_spring.project_web;

public class TaskHasNoTitle extends RuntimeException {
    public TaskHasNoTitle() {
        super("Task has no title");
    }
}
