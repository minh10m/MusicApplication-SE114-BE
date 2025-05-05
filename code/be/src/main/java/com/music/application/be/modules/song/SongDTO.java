package com.music.application.be.modules.song;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SongDTO {
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    private Integer duration;
    private String audioUrl;
    private String thumbnail;
    private String lyrics;
    private LocalDate releaseDate;
    private Integer viewCount;
    private Long artistId;
    private Long albumId;
    private List<Long> genreIds;
}