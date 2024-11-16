package com.banquemisr.challenge05.service.Task;

import com.banquemisr.challenge05.enums.TaskStatus;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.request.TaskRequest;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService {
    Task createTask(TaskRequest request);

    Task updateTask(Long taskId, TaskRequest request);

    void deleteTask(Long taskId);

    Task getTaskById(Long taskId);

    List<Task> getAllTasks();

    Task changeTaskStatus(Long taskId, TaskStatus newStatus);

    List<Task> searchByTitle(String title);

    List<Task> searchByDescription(String description);

    List<Task> searchByStatus(TaskStatus status);

    List<Task> searchByDueDate(LocalDate dueDate);

    Task markTaskAsCompleted(Long taskId);

    Task markTaskAsInProgress(Long taskId) ;

    void notifyUsersOfUpcomingTasks(LocalDate fromDate, LocalDate toDate);

    List<Task> getUpcomingTasksFrom(LocalDate fromDate, LocalDate toDate);

}
