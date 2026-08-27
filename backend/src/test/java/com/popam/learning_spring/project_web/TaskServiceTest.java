package com.popam.learning_spring.project_web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void getTaskById_returnTask_whenTaskExistsAndBelongsToUser() {
        //Arrange - data preparation
        Task task = new Task();
        task.setId(1);
        task.setTitle("Test task");
        task.setDescription("Test task description");
        User user = new User();
        user.setUsername("testUser");
        task.setUser(user);

        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        // Act - call the method
        TaskResponseDTO result = taskService.getTaskById(1, "testUser");

        //Assert - verify the result
        assertEquals("Test task", result.getTitle());
        assertEquals(1, result.getId());

    }

    @Test
    void getTaskById_throwsAccessDenied_whenTaskBelongsToAnotherUser() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        task.setTitle("Test task");
        task.setDescription("Test task description");

        User user = new User();
        user.setUsername("anotherTestUser");

        task.setUser(user);

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(task));

        // Act + Assert
        assertThrows(
                AccessDeniedException.class,
                () -> taskService.getTaskById(1, "testUser")
        );
    }

    @Test
    void getTaskById_returnsTask() {
        // Arrange
        Task task = new Task();
        task.setId(1);
        task.setTitle("Test task");
        task.setDescription("Test description");

        User user = new User();
        user.setUsername("testUser");
        task.setUser(user);

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(task));

        // Act
        TaskResponseDTO result = taskService.getTaskById(1, "testUser");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test task", result.getTitle());
        assertEquals("Test description", result.getDescription());
        assertEquals("testUser", result.getUsername());

        verify(taskRepository).findById(1);
    }


    @Test
    void getTaskById_throwsTaskNotFound() {
        //Arrange
        when(taskRepository.findById(1))
                .thenReturn(Optional.empty());

        // Assert
        assertThrows(
                TaskNotFound.class,
                () -> taskService.getTaskById(1, "testUser")
        );

        verify(taskRepository).findById(1);
    }

    @Test
    void getAllTasks_returnsTasksForUser() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");

        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("Test task1");
        task1.setUser(user);

        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("Test task2");
        task2.setUser(user);

        Task task3 = new Task();
        task3.setId(3);
        task3.setTitle("Test task3");
        task3.setUser(user);

        when(taskRepository.findByUserUsername("testUser"))
                .thenReturn(List.of(task1, task2, task3));

        // Act
        List<TaskResponseDTO> result = taskService.getAllTasks("testUser");

        // Assert
        assertEquals(3, result.size());
    }

    @Test
    void getAllTasks_returnsEmptyList(){
        List<TaskResponseDTO> result = taskService.getAllTasks("testUser");
        assertEquals(0, result.size());
    }

    @Test
    void createTask_createsTask() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("Test task");
        request.setDescription("Test description");
        request.setCompleted(false);
        request.setPriority(Priority.HIGH);

        Task savedTask = new Task();
        savedTask.setId(1);
        savedTask.setTitle("Test task");
        savedTask.setDescription("Test description");
        savedTask.setCompleted(false);
        savedTask.setPriority(Priority.HIGH);
        savedTask.setUser(user);

        when(userRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(user));

        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        // Act
        TaskResponseDTO result = taskService.createTask(request, "testUser");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test task", result.getTitle());
        assertEquals("Test description", result.getDescription());
        assertEquals(false, result.getCompleted());
        assertEquals(Priority.HIGH, result.getPriority());
        assertEquals("testUser", result.getUsername());

        verify(userRepository).findByUsername("testUser");
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_throwsUserNotFound() {
        when(userRepository.findByUsername("testUser"))
        .thenReturn(Optional.empty());

        assertThrows(
                UserNotFound.class,
                () -> taskService.createTask(new TaskRequestDTO(), "testUser")
        );
    }

    @Test
    void createTask_defaultsCompletedToFalse() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(user));

        TaskRequestDTO request = new TaskRequestDTO();
        request.setCompleted(null);

        Task savedTask = new Task();
        savedTask.setId(1);
        savedTask.setCompleted(false);
        savedTask.setUser(user);

        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);

        // Act
        TaskResponseDTO result = taskService.createTask(request, "testUser");

        // Assert
        assertFalse(result.getCompleted());
    }

    @Test
    void updateTask_updatesTask() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");

        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("New title");
        request.setDescription("New description");
        request.setCompleted(true);

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskResponseDTO result =
                taskService.updateTask(1, request, "testUser");

        // Assert
        assertEquals("New title", result.getTitle());
        assertEquals("New description", result.getDescription());
        assertTrue(result.getCompleted());

        verify(taskRepository).findById(1);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void updateTask_throwsTaskNotFound() {
        // Arrange
        when(taskRepository.findById(1))
                .thenReturn(Optional.empty());

        TaskRequestDTO request = new TaskRequestDTO();

        // Act + Assert
        assertThrows(
                TaskNotFound.class,
                () -> taskService.updateTask(1, request, "testUser")
        );

        verify(taskRepository).findById(1);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_throwsAccessDenied() {
        // Arrange
        User user = new User();
        user.setUsername("anotherTestUser");

        Task task = new Task();
        task.setId(1);
        task.setTitle("Test task");
        task.setUser(user);

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("New title");

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(task));

        // Act + Assert
        assertThrows(
                AccessDeniedException.class,
                () -> taskService.updateTask(1, request, "testUser")
        );

        verify(taskRepository).findById(1);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void patchTask_updatesProvidedFields() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");

        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("New title");
        request.setCompleted(true);

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskResponseDTO result =
                taskService.patchTask(1, request, "testUser");

        // Assert
        assertEquals("New title", result.getTitle());
        assertEquals("Old description", result.getDescription());
        assertTrue(result.getCompleted());

        verify(taskRepository).save(existingTask);
    }

    @Test
    void patchTask_preservesNullFields() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");

        Task existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTitle("Old title");
        existingTask.setDescription("Old description");
        existingTask.setCompleted(false);
        existingTask.setUser(user);

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle(null);
        request.setDescription(null);
        request.setCompleted(null);

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TaskResponseDTO result =
                taskService.patchTask(1, request, "testUser");

        // Assert
        assertEquals("Old title", result.getTitle());
        assertEquals("Old description", result.getDescription());
        assertFalse(result.getCompleted());
    }

    @Test
    void patchTask_throwsTaskNotFound() {
        // Arrange
        when(taskRepository.findById(1))
                .thenReturn(Optional.empty());

        TaskRequestDTO request = new TaskRequestDTO();

        // Act + Assert
        assertThrows(
                TaskNotFound.class,
                () -> taskService.patchTask(1, request, "testUser")
        );

        verify(taskRepository).findById(1);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void patchTask_throwsAccessDenied() {
        // Arrange
        User user = new User();
        user.setUsername("anotherTestUser");

        Task task = new Task();
        task.setId(1);
        task.setTitle("Test task");
        task.setUser(user);

        TaskRequestDTO request = new TaskRequestDTO();
        request.setTitle("New title");

        when(taskRepository.findById(1))
                .thenReturn(Optional.of(task));

        // Act + Assert
        assertThrows(
                AccessDeniedException.class,
                () -> taskService.patchTask(1, request, "testUser")
        );

        verify(taskRepository).findById(1);
        verify(taskRepository, never()).save(any(Task.class));
    }
}
