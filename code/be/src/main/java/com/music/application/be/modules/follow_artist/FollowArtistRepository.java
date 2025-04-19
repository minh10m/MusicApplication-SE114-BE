package com.music.application.be.modules.follow_artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowArtistRepository extends JpaRepository<FollowArtist, Long> {
    List<FollowArtist> findByUserId(Long userId);
    boolean existsByUserIdAndArtistId(Long userId, Long artistId);
}