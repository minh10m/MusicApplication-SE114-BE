package com.music.application.be.modules.song_playlist.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SongPlaylistDTO {
    private Long id;
    private Long songId;
    private Long playlistId;
    private LocalDateTime addedAt;
}