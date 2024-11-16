package com.banquemisr.challenge05.taskServiceTestCases;

import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.model.User;
import com.banquemisr.challenge05.repository.TaskRepository;
import com.banquemisr.challenge05.enums.TaskStatus;
import com.banquemisr.challenge05.enums.TaskPriority;
import com.banquemisr.challenge05.request.TaskRequest;
import com.banquemisr.challenge05.service.Email.EmailService;
import com.banquemisr.challenge05.service.History.IHistoryService;
import com.banquemisr.challenge05.service.Task.TaskService;
import com.banquemisr.challenge05.service.User.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private IHistoryService historyService;

    @Mock
    private IUserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskRequest taskRequest;
    private User user;

    @BeforeEach
    void setUp() {
        taskRequest = new TaskRequest();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setPriority(TaskPriority.LOW);
        taskRequest.setDueDate(LocalDate.now().plusDays(5));

        user = new User();
        user.setId(1L);
        user.setUsername("test_user");
        user.setEmail("test_user@example.com");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setPriority(TaskPriority.LOW);
        task.setDueDate(LocalDate.now().plusDays(5));
        task.setStatus(TaskStatus.TODO);
        task.setUser(user);
    }
    @Test
    void testCreateTask_Success() {

        Mockito.when(taskRepository.existsByTitle(taskRequest.getTitle())).thenReturn(false);
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(user);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
        Task result = taskService.createTask(taskRequest);
        assertNotNull(result);
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(TaskStatus.TODO, result.getStatus());
    }
    
    @Test
    void testUpdateTask_Success() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(user);
        Mockito.doNothing().when(userService).checkTaskOwnership(task.getId(), user);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        TaskRequest updateRequest = new TaskRequest();
        updateRequest.setTitle("Updated Task");
        updateRequest.setDescription("Updated Description");
        updateRequest.setDueDate(LocalDate.of(2024,12,30));
        updateRequest.setPriority(TaskPriority.MEDIUM);

        Task result = taskService.updateTask(task.getId(), updateRequest);


        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    void testDeleteTask_Success() {
        Mockito.when(userService.getAuthenticatedUser()).thenReturn(user);
        Mockito.doNothing().when(userService).checkTaskOwnership(task.getId(), user);
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        taskService.deleteTask(task.getId());
        Mockito.verify(taskRepository).delete(task);
    }

    @Test
    void testGetTaskById_Success() {
        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(task.getId());

        assertNotNull(result);
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getDescription(), result.getDescription());

        Mockito.verify(taskRepository).findById(task.getId());
    }
    @Test
    void testNotifyUsersOfUpcomingTasks_Success() {
        Mockito.when(taskRepository.findAllByDueDateBetween(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(List.of(task));
        taskService.notifyUsersOfUpcomingTasks(LocalDate.now(), LocalDate.now().plusDays(5));

        Mockito.verify(emailService).sendEmail(
                Mockito.eq(task.getUser().getEmail()),
                Mockito.anyString(),
                Mockito.anyString()
        );
    }
}
