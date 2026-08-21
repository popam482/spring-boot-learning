package com.popam.learning_spring.project_web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> getTasks(Authentication authentication) {
        return taskService.getAllTasks(authentication.getName());
    }

    @GetMapping("/tasks/{id}")
    public TaskResponseDTO getTask(@PathVariable Integer id, Authentication authentication) {
        return taskService.getTaskById(id, authentication.getName());
    }

    @GetMapping("/tasks/search")
    public List<TaskResponseDTO> getTask(@RequestParam Boolean completed, Authentication authentication) {
        return taskService.searchByCompleted(completed, authentication.getName());
    }

    @PostMapping("/tasks")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO task, Authentication authentication) {
        return taskService.createTask(task, authentication.getName());
    }

    @PatchMapping("/tasks/{id}")
    public TaskResponseDTO patchTask(@PathVariable Integer id,
                                     @RequestBody TaskRequestDTO updatedTask,
                                     Authentication authentication) {
        return taskService.patchTask(id, updatedTask, authentication.getName());
    }

    @PutMapping("/tasks/{id}")
    public TaskResponseDTO updateTask(@PathVariable Integer id,
                                      @Valid @RequestBody TaskRequestDTO updatedTask,
                                      Authentication authentication) {
        return taskService.updateTask(id, updatedTask, authentication.getName());
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer id, Authentication authentication) {
        taskService.deleteTask(id, authentication.getName());
    }
}