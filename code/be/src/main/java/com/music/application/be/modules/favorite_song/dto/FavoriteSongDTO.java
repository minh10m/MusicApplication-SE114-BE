package com.music.application.be.modules.favorite_song.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteSongDTO {
    private Long id;
    private Long userId;
    private Long songId;
    private LocalDateTime addedAt;
}