package com.music.application.be.modules.follow_artist.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowArtistDTO {
    private Long id;
    private Long userId;
    private Long artistId;
    private LocalDateTime followedAt;
}