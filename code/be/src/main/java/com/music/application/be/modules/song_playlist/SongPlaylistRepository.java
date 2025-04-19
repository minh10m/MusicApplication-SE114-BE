package com.music.application.be.modules.song_playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, Long> {
    List<SongPlaylist> findByPlaylistId(Long playlistId);
}
