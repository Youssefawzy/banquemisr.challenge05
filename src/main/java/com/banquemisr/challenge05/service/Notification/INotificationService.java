package com.banquemisr.challenge05.service.Notification;

import com.banquemisr.challenge05.model.Notification;
import com.banquemisr.challenge05.request.NotificationRequest;

import java.util.List;

public interface INotificationService {

    Notification createNotification(Long taskId, NotificationRequest request);

    Notification getNotificationById(Long notificationId);

    List<Notification> getNotificationsByTaskId(Long taskId);

    Notification updateNotification(Long notificationId, NotificationRequest request);

    void deleteNotification(Long notificationId);
}
