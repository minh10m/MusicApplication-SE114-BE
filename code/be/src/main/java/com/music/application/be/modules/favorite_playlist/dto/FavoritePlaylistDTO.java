package com.music.application.be.modules.favorite_playlist.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FavoritePlaylistDTO {
    private Long id;
    private Long userId;
    private Long playlistId;
    private LocalDateTime addedAt;
}