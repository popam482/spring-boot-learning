package com.popam.learning_spring.project_web;

public class TaskIdAddedByUser extends RuntimeException {
    public TaskIdAddedByUser() {
        super("The id cannot be added by the user.");
    }
}
