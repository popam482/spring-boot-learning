package com.popam.learning_spring.project_web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(TaskNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTaskNotFoundException(TaskNotFound e) {
        return e.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TaskIdAddedByUser.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTaskIdAddedByUser(TaskIdAddedByUser e) {
        return e.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TaskHasNoTitle.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTaskHasNoTitle(TaskHasNoTitle e) {
        return e.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TaskHasNoDescription.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTaskHasNoDescription(TaskHasNoDescription e) {
        return e.getMessage();
    }

    @ExceptionHandler(TaskCompleted.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleTaskCompleted(TaskCompleted e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return error;
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    @ExceptionHandler(UserNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(UserNotFound e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return errors;
    }

    @ExceptionHandler(InvalidCredentials.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleInvalidCredentials(InvalidCredentials e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return error;
    }
}
