package com.popam.learning_spring.project_web;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials() {
        super("Invalid username or password");
    }
}
