package com.popam.learning_spring.project_web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Task {
    private Integer id;
    private @NotBlank String title;
    private String description;
    private @NotNull Priority priority;
    private Boolean completed;

    public Task() {}

    public Task(Integer id, String title, String description, Priority priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }

    public Task(Integer id, String title, String description, Priority priority) {
        this(id, title, description, priority, false);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
