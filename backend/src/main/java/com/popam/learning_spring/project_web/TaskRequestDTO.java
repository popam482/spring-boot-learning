package com.popam.learning_spring.project_web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull
    private Boolean completed;

    @NotNull
    private Priority priority;

    @NotNull(message = "User id cannot be null")
    private Integer userId;

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, Boolean completed, String description, Priority priority, Integer userId) {
        this.title = title;
        this.completed = completed;
        this.description = description;
        this.priority = priority;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
