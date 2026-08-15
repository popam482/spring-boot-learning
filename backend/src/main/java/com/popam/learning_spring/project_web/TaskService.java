package com.popam.learning_spring.project_web;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TaskResponseDTO getTaskById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));
        return toResponseDTO(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        Task task = toEntity(requestDTO);

        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }

        Task savedTask = taskRepository.save(task);
        return toResponseDTO(savedTask);
    }

    public TaskResponseDTO updateTask(Integer id, TaskRequestDTO requestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFound(requestDTO.getUserId()));

        existingTask.setTitle(requestDTO.getTitle());
        existingTask.setDescription(requestDTO.getDescription());
        existingTask.setCompleted(requestDTO.getCompleted() != null ? requestDTO.getCompleted() : false);
        existingTask.setPriority(requestDTO.getPriority());
        existingTask.setUser(user);

        Task savedTask = taskRepository.save(existingTask);
        return toResponseDTO(savedTask);
    }

    public TaskResponseDTO patchTask(Integer id, TaskRequestDTO requestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));

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
        if (requestDTO.getUserId() != null) {
            User user = userRepository.findById(requestDTO.getUserId())
                    .orElseThrow(() -> new UserNotFound(requestDTO.getUserId()));
            existingTask.setUser(user);
        }

        Task savedTask = taskRepository.save(existingTask);
        return toResponseDTO(savedTask);
    }

    public void deleteTask(Integer id) {
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFound(id));

        if (Boolean.TRUE.equals(taskToDelete.getCompleted())) {
            throw new TaskCompleted(id);
        }

        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> searchByCompleted(Boolean completed) {
        return taskRepository.findTasksByCompleted(completed).stream()
                .map(this::toResponseDTO)
                .toList();
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

    private Task toEntity(TaskRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFound(dto.getUserId()));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setPriority(dto.getPriority());
        task.setUser(user);

        return task;
    }
}
