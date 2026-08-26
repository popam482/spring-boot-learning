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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
