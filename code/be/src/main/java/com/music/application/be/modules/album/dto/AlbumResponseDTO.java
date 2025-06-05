package com.music.application.be.modules.album.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlbumResponseDTO {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private String coverImage;
    private String description;
    private Long artistId;
}