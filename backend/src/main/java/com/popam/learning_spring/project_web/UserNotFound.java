package com.popam.learning_spring.project_web;

public class UserNotFound extends RuntimeException {
    public UserNotFound(Integer id) {
        super("User with id " + id + " not found: ");
    }
}
