package com.music.application.be.modules.downloaded_song;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DownloadedSongDTO {
    private Long id;
    private Long userId;
    private Long songId;
    private LocalDateTime downloadedAt;
}
