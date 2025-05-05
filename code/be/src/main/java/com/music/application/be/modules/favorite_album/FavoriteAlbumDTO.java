package com.music.application.be.modules.favorite_album;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FavoriteAlbumDTO {
    private Long id;
    private Long userId;
    private Long albumId;
    private LocalDateTime addedAt;
}