package com.popam.learning_spring.project_web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public TaskResponseDTO getTask(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/tasks/search")
    public List<TaskResponseDTO> getTask(@RequestParam Boolean completed) {
        return taskService.searchByCompleted(completed);
    }

    @PostMapping("/tasks")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO task) {
        return taskService.createTask(task);
    }

    @PatchMapping("/tasks/{id}")
    public TaskResponseDTO patchTask(@PathVariable Integer id,
                                     @RequestBody TaskRequestDTO updatedTask) {
        return taskService.patchTask(id, updatedTask);
    }

    @PutMapping("/tasks/{id}")
    public TaskResponseDTO updateTask(@PathVariable Integer id, @Valid @RequestBody TaskRequestDTO updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }
}