package com.banquemisr.challenge05.controller;

import com.banquemisr.challenge05.service.Task.ITaskService;
import com.banquemisr.challenge05.enums.TaskStatus;
import com.banquemisr.challenge05.model.History;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.request.TaskRequest;
import com.banquemisr.challenge05.service.History.IHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskService taskService;
    private final IHistoryService historyService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        Task createdTask = taskService.createTask(taskRequest);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/history/{taskId}")
    public ResponseEntity<List<History>> getTaskHistoriesByTaskId(@PathVariable Long taskId) {
        List<History> history = historyService.getTaskHistoryByTaskId(taskId);
        return ResponseEntity.ok(history);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId,
                                           @Valid @RequestBody TaskRequest taskRequest) {
        Task updatedTask = taskService.updateTask(taskId, taskRequest);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Task> changeTaskStatus(@PathVariable Long taskId,
                                                 @RequestBody TaskStatus newStatus) {
        Task updatedTask = taskService.changeTaskStatus(taskId, newStatus);
        return ResponseEntity.ok(updatedTask);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskAsCompleted(@PathVariable Long taskId) {
        Task completedTask = taskService.markTaskAsCompleted(taskId);
        return ResponseEntity.ok(completedTask);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PatchMapping("/{taskId}/in_progress")
    public ResponseEntity<Task> markTaskAsInProgress(@PathVariable Long taskId) {
        Task completedTask = taskService.markTaskAsInProgress(taskId);
        return ResponseEntity.ok(completedTask);
    }


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/search/title")
    public ResponseEntity<List<Task>> searchByTitle(@RequestParam String title) {
        List<Task> tasks = taskService.searchByTitle(title);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/search/description")
    public ResponseEntity<List<Task>> searchByDescription(@RequestParam String description) {
        List<Task> tasks = taskService.searchByDescription(description);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/search/status")
    public ResponseEntity<List<Task>> searchByStatus(@RequestParam TaskStatus status) {
        List<Task> tasks = taskService.searchByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/search/due-date")
    public ResponseEntity<List<Task>> searchByDueDate(@RequestParam LocalDate dueDate) {
        List<Task> tasks = taskService.searchByDueDate(dueDate);
        return ResponseEntity.ok(tasks);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/Notify-Upcoming-Tasks")
    public ResponseEntity<String> notifyUsersOfUpcomingTasks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        taskService.notifyUsersOfUpcomingTasks(fromDate, toDate);
        return ResponseEntity.ok("Notifications sent for tasks between " + fromDate + " and " + toDate);
    }

}
