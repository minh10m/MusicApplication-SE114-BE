package com.music.application.be.modules.notification.dto;


import com.music.application.be.modules.notification.Notification;
import com.music.application.be.modules.notification.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private NotificationType type;
    private boolean read;
    private LocalDateTime createdAt;

    public static NotificationResponse fromEntity(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setUserId(notification.getUser().getId());
        response.setTitle(notification.getTitle());
        response.setContent(notification.getContent());
        response.setType(notification.getType());
        response.setRead(notification.isRead());
        response.setCreatedAt(notification.getCreatedAt());
        return response;
    }
}
