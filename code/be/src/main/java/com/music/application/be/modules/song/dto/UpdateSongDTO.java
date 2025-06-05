package com.music.application.be.modules.song.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateSongDTO {
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    private String lyrics;
    private LocalDate releaseDate;
    private Integer viewCount;
    private Long artistId;
    private Long albumId;
    private List<Long> genreIds;
}
