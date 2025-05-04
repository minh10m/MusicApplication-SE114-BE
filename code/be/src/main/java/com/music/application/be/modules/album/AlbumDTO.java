package com.music.application.be.modules.album;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlbumDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    private LocalDate releaseDate;

    private String coverImage;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    private Long artistId;

    private List<Long> songIds;
}