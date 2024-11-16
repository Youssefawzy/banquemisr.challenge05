package com.banquemisr.challenge05.controller;
import com.banquemisr.challenge05.model.Notification;
import com.banquemisr.challenge05.service.Notification.INotificationService;
import com.banquemisr.challenge05.request.NotificationRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final INotificationService notificationService;

    @PostMapping("/task/{taskId}")
    public ResponseEntity<Notification> createNotification(
            @PathVariable Long taskId, @Valid @RequestBody NotificationRequest request) {
        Notification notification = notificationService.createNotification(taskId, request);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Notification>> getNotificationsByTaskId(@PathVariable Long taskId) {
        List<Notification> notifications = notificationService.getNotificationsByTaskId(taskId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Long notificationId, @Valid  @RequestBody NotificationRequest request) {
        Notification updatedNotification = notificationService.updateNotification(notificationId, request);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
