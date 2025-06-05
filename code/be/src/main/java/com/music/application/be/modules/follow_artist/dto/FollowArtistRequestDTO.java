package com.music.application.be.modules.follow_artist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FollowArtistRequestDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Artist ID is required")
    private Long artistId;
}