package com.banquemisr.challenge05.service.Task;

import com.banquemisr.challenge05.enums.HistoryAction;
import com.banquemisr.challenge05.enums.TaskStatus;
import com.banquemisr.challenge05.exception.TaskAlreadyExistsException;
import com.banquemisr.challenge05.exception.TaskNotFoundException;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.model.User;
import com.banquemisr.challenge05.repository.TaskRepository;
import com.banquemisr.challenge05.request.TaskRequest;
import com.banquemisr.challenge05.service.Email.EmailService;
import com.banquemisr.challenge05.service.History.IHistoryService;
import com.banquemisr.challenge05.service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final IHistoryService historyService;
    private final IUserService userService;
    private final EmailService emailService;


    @Override
    public Task createTask( TaskRequest request) {
        System.out.println("here");

        // Check if a task with the same title already exists
        if (taskRepository.existsByTitle(request.getTitle())) {
            throw new TaskAlreadyExistsException("Task with title '" + request.getTitle() + "' already exists",409);
        }

        // Fetch the authenticated user
        User currentUser = userService.getAuthenticatedUser();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setStatus(TaskStatus.TODO);
        task.setUser(currentUser); // Associate the task with the authenticated user


        historyService.addHistory(task, HistoryAction.CREATED);


        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, TaskRequest request) {
        User currentUser = userService.getAuthenticatedUser();
        userService.checkTaskOwnership(taskId , currentUser);

        // Check if the task exists
            Task existingTask = taskRepository.findById(taskId)
                    .orElseThrow(() ->
                            new TaskNotFoundException("Task with ID " + taskId + " not found", 404));

            modelMapper.getConfiguration().setSkipNullEnabled(true);
            //if the request has a null will not override the existingTask
            modelMapper.map(request, existingTask);

             // Save updated task
            historyService.addHistory(existingTask, HistoryAction.UPDATED);

            return taskRepository.save(existingTask);

    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {

        User currentUser = userService.getAuthenticatedUser();
        userService.checkTaskOwnership(taskId , currentUser);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task with ID " + taskId + " not found", 404));

        taskRepository.delete(task);
    }
    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId + " not found", 404));
    }


    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getUpcomingTasksFrom(LocalDate fromDate, LocalDate toDate) {
        return taskRepository.findAllByDueDateBetween(fromDate, toDate);
    }

    @Override
    public List<Task> searchByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
    @Override
    public List<Task> searchByDescription(String description) {
        return taskRepository.findByDescriptionContainingIgnoreCase(description);
    }
    @Override
    public List<Task> searchByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    @Override
    public List<Task> searchByDueDate(LocalDate dueDate) {
        return taskRepository.findTasksDueOnOrBefore(dueDate);
    }

    @Override
    public Task changeTaskStatus(Long taskId, TaskStatus newStatus) {
        User currentUser = userService.getAuthenticatedUser();
        userService.checkTaskOwnership(taskId , currentUser);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId,404));

        if (newStatus == TaskStatus.DONE) {
            historyService.addHistory(task, HistoryAction.COMPLETED);
        } else if (newStatus == TaskStatus.IN_PROGRESS) {
            historyService.addHistory(task, HistoryAction.IN_PROGRESS);
        }
        task.setStatus(newStatus);

        return taskRepository.save(task);
    }

    @Override
    public Task markTaskAsInProgress(Long taskId) {

        return changeTaskStatus(taskId, TaskStatus.IN_PROGRESS);
    }
    @Override
    public Task markTaskAsCompleted(Long taskId) {
        return changeTaskStatus(taskId, TaskStatus.DONE);
    }

    public void notifyUsersOfUpcomingTasks(LocalDate fromDate, LocalDate toDate) {
        List<Task> upcomingTasks = getUpcomingTasksFrom(fromDate, toDate);

        if (upcomingTasks.isEmpty()) {
            System.out.println("No upcoming tasks found for the specified date range.");
            return;
        }

        for (Task task : upcomingTasks) {
            User user = task.getUser();
            String emailBody = buildEmailBody(task);

            try {
                emailService.sendEmail(
                        user.getEmail(),
                        "Upcoming Task Reminder",
                        emailBody
                );
                System.out.println("Notification sent to: " + user.getEmail());
            } catch (Exception e) {
                System.err.println("Error sending email to " + user.getEmail() + ": " + e.getMessage());
            }
        }
    }


    private String buildEmailBody(Task task) {
        return "Dear " + task.getUser().getUsername() + ",\n\n" +
                "You have an upcoming task:\n" +
                "Title: " + task.getTitle() + "\n" +
                "Description: " + task.getDescription() + "\n" +
                "Deadline: " + task.getDueDate() + "\n\n" +
                "Please ensure to complete it on time.\n\n" +
                "Best regards,\nTask Management System";
    }


}
