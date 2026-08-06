package com.popam.learning_spring.project_web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/tasks/search")
    public List<Task> getTask(@RequestParam Boolean completed) {
        return taskService.searchByCompleted(completed);
    }

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PatchMapping("/tasks/{id}")
    public Task patchTask(@PathVariable Integer id,
                          @RequestBody Task updatedTask) {
        return taskService.patchTask(id, updatedTask);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Integer id, @Valid @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer id) {
       taskService.deleteTask(id);
    }
}
