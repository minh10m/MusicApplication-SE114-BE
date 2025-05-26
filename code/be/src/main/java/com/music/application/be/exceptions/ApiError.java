package com.music.application.be.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String details;
}