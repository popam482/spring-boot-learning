package com.popam.learning_spring.project_web;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getAllTasks(String username) {
        return taskRepository.findByUserUsername(username)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO getTaskById(Integer id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));
        verifyOwnership(task, username);
        return toResponseDTO(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setCompleted(requestDTO.getCompleted() != null ? requestDTO.getCompleted() : false);
        task.setPriority(requestDTO.getPriority());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return toResponseDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Integer id, TaskRequestDTO requestDTO, String username) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));

        verifyOwnership(existingTask, username);

        existingTask.setTitle(requestDTO.getTitle());
        existingTask.setDescription(requestDTO.getDescription());
        existingTask.setCompleted(requestDTO.getCompleted() != null ? requestDTO.getCompleted() : false);
        existingTask.setPriority(requestDTO.getPriority());

        Task savedTask = taskRepository.save(existingTask);
        return toResponseDTO(savedTask);
    }

    public TaskResponseDTO patchTask(Integer id, TaskRequestDTO requestDTO, String username) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));

        verifyOwnership(existingTask, username);

        if (requestDTO.getTitle() != null) {
            existingTask.setTitle(requestDTO.getTitle());
        }
        if (requestDTO.getDescription() != null) {
            existingTask.setDescription(requestDTO.getDescription());
        }
        if (requestDTO.getCompleted() != null) {
            existingTask.setCompleted(requestDTO.getCompleted());
        }
        if (requestDTO.getPriority() != null) {
            existingTask.setPriority(requestDTO.getPriority());
        }

        Task savedTask = taskRepository.save(existingTask);
        return toResponseDTO(savedTask);
    }

    public void deleteTask(Integer id, String username) {
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));

        verifyOwnership(taskToDelete, username);

        if (Boolean.TRUE.equals(taskToDelete.getCompleted())) {
            throw new TaskCompleted(id);
        }

        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> searchByCompleted(Boolean completed, String username) {
        return taskRepository.findTasksByCompletedAndUserUsername(completed, username).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private void verifyOwnership(Task task, String username) {
        if (task.getUser() == null || !task.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You do not have permission to modify this task");
        }
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setPriority(task.getPriority());

        if (task.getUser() != null) {
            dto.setUser_id(task.getUser().getUser_id());
            dto.setUsername(task.getUser().getUsername());
        }

        return dto;
    }
}