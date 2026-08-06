package com.popam.learning_spring.project_web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    List<Task> taskList = new ArrayList<>();

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskList;
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable Integer id) {
        return taskList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(id));
    }

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        if (task.getId() != null) {
            throw new TaskIdAddedByUser();
        }
        int nextId = taskList.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0);
        task.setId(nextId + 1);
        taskList.add(task);
        return task;
    }

    @PatchMapping("/tasks/{id}")
    public Task patchTask(@PathVariable Integer id,
                          @RequestBody Task updatedTask) {

        Task taskToUpdate = taskList.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(id));

        if (updatedTask.getId() != null) {
            throw new TaskIdAddedByUser();
        }

        if (updatedTask.getTitle() != null) {
            if (updatedTask.getTitle().isBlank()) {
                throw new TaskHasNoTitle();
            }
            taskToUpdate.setTitle(updatedTask.getTitle());
        }

        if (updatedTask.getDescription() != null) {
            if (updatedTask.getDescription().isBlank()) {
                throw new TaskHasNoDescription();
            }
            taskToUpdate.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getPriority() != null) {
            taskToUpdate.setPriority(updatedTask.getPriority());
        }

        if (updatedTask.getCompleted() != null) {
            taskToUpdate.setCompleted(updatedTask.getCompleted());
        }

        return taskToUpdate;
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Integer id, @Valid @RequestBody Task updatedTask) {
        Task taskToUpdate = taskList.stream()
                .filter(task1 -> task1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(id));

        taskToUpdate.setTitle(updatedTask.getTitle());
        taskToUpdate.setDescription(updatedTask.getDescription());
        taskToUpdate.setPriority(updatedTask.getPriority());
        taskToUpdate.setCompleted(updatedTask.getCompleted());

        return taskToUpdate;
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer id) {
        Task taskToDelete = taskList.stream()
                .filter(task1 -> task1.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(id));

        taskList.remove(taskToDelete);
    }
}
