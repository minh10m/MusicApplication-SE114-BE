package com.music.application.be.modules.favorite_song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteSongRepository extends JpaRepository<FavoriteSong, Long> {
    List<FavoriteSong> findByUserId(Long userId);
    boolean existsByUserIdAndSongId(Long userId, Long songId);
}
