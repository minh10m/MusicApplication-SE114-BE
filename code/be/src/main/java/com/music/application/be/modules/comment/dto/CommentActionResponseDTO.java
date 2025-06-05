package com.music.application.be.modules.comment.dto;

import lombok.Data;

@Data
public class CommentActionResponseDTO {
    private boolean success;
    private String message;
    private CommentResponseDTO comment;
}