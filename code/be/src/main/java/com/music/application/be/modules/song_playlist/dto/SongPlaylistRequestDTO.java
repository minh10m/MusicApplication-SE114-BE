package com.music.application.be.modules.song_playlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SongPlaylistRequestDTO {
    @NotNull(message = "Song ID is required")
    private Long songId;

    @NotNull(message = "Playlist ID is required")
    private Long playlistId;
}