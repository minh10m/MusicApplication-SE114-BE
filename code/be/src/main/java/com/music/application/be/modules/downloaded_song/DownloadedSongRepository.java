package com.music.application.be.modules.downloaded_song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadedSongRepository extends JpaRepository<DownloadedSong, Long> {
    List<DownloadedSong> findByUserId(Long userId);
    boolean existsByUserIdAndSongId(Long userId, Long songId);
}
