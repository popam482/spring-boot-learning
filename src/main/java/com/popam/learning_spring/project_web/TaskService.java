package com.popam.learning_spring.project_web;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        Optional<Task> taskById = taskRepository.findById(id);
        if (taskById.isEmpty()) {
            throw new TaskNotFound(id);
        }
        return taskById.get();
    }

    public Task createTask(Task task) {
        if (task.getId() != null) {
            throw new TaskIdAddedByUser();
        }

        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }

        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        existingTask.setPriority(updatedTask.getPriority());

        return taskRepository.save(existingTask);
    }

    public Task patchTask(Integer id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        if (updatedTask.getTitle() != null) {
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getCompleted() != null) {
            existingTask.setCompleted(updatedTask.getCompleted());
        }
        if (updatedTask.getPriority() != null) {
            existingTask.setPriority(updatedTask.getPriority());
        }

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Integer id) {
        Task taskToDelete = getTaskById(id);
        if(taskToDelete.getCompleted() == true) {
            throw new TaskCompleted(id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> searchByCompleted(Boolean completed) {
        return taskRepository.findTasksByCompleted(completed);
    }

}
