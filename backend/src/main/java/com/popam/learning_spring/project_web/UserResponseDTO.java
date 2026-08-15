package com.popam.learning_spring.project_web;

public class UserResponseDTO {
    private Integer id;
    private String username;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Integer id, String password, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
