package com.music.application.be.modules.artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ArtistDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    private String avatar;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    private int followerCount;

    private List<Long> albumIds; // Giữ albumIds để hỗ trợ lấy danh sách album
}