package com.banquemisr.challenge05.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequest {
    private String message;
    private LocalDateTime sentAt;
}
