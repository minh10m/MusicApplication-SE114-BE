package com.music.application.be.modules.notification.dto;

import com.music.application.be.modules.notification.NotificationType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateNotificationRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Type is required")
    private NotificationType type;
}