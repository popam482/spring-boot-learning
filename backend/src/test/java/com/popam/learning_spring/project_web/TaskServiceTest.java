package com.popam.learning_spring.project_web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
