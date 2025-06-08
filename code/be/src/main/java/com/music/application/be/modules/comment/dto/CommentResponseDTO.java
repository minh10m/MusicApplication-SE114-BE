package com.music.application.be.modules.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponseDTO {
    private Long id;
    private Long songId;
    private Long userId;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
    private Long likes;
    private List<CommentResponseDTO> replies;
}