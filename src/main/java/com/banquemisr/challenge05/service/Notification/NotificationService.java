package com.banquemisr.challenge05.service.Notification;

import com.banquemisr.challenge05.exception.NotificationNotFoundException;
import com.banquemisr.challenge05.model.Notification;
import com.banquemisr.challenge05.model.Task;
import com.banquemisr.challenge05.repository.NotificationRepository;
import com.banquemisr.challenge05.repository.TaskRepository;
import com.banquemisr.challenge05.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService{

    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public Notification createNotification(Long taskId, NotificationRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotificationNotFoundException("Task with ID " + taskId + " not found", 404));

        Notification notification = new Notification();
        notification.setTask(task);
        notification.setMessage(request.getMessage());
        notification.setSentAt(request.getSentAt() != null ? request.getSentAt() : LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + notificationId + " not found", 404));
    }

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByTaskId(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotificationNotFoundException("Task with ID " + taskId + " not found", 404));

        return notificationRepository.findByTask(task);
    }

    @Transactional
    public Notification updateNotification(Long notificationId, NotificationRequest request) {
        Notification existingNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + notificationId + " not found", 404));

        if (request.getMessage() != null) {
            existingNotification.setMessage(request.getMessage());
        }
        if (request.getSentAt() != null) {
            existingNotification.setSentAt(request.getSentAt());
        }

        return notificationRepository.save(existingNotification);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with ID " + notificationId + " not found", 404));

        notificationRepository.delete(notification);
    }
}
