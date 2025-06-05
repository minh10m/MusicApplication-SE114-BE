package com.music.application.be.modules.comment.dto;

    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.Data;

    @Data
    public class CreateCommentDTO {
        @NotBlank(message = "Content is required")
        @Size(max = 1000, message = "Content must not exceed 1000 characters")
        private String content;

        @NotNull(message = "User ID is required")
        private Long userId;

        private Long parentId;
    }