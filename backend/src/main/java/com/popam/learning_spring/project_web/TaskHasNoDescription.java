package com.popam.learning_spring.project_web;

public class TaskHasNoDescription extends RuntimeException {
    public TaskHasNoDescription() {

        super("Task has no description");
    }
}
