package com.popam.learning_spring.project_web;

public class TaskResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private Priority priority;
    private Boolean completed;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(Integer id, String title, String description, Boolean completed, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
