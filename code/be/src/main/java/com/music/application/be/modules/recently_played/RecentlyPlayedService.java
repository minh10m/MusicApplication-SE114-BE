package com.music.application.be.modules.recently_played;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecentlyPlayedService {

    private final RecentlyPlayedRepository recentlyPlayedRepository;

    @CacheEvict(value = "recentlyPlayed", key = "#user.id")
    public RecentlyPlayed addRecentlyPlayed(User user, Song song) {
        RecentlyPlayed recentlyPlayed = RecentlyPlayed.builder()
                .user(user)
                .song(song)
                .build();
        return recentlyPlayedRepository.save(recentlyPlayed);
    }

    @Cacheable(value = "recentlyPlayed", key = "#user.id")
    public List<RecentlyPlayed> getRecentlyPlayedByUser(User user) {
        return recentlyPlayedRepository.findByUserOrderByPlayedAtDesc(user);
    }

    @CacheEvict(value = "recentlyPlayed", key = "#user.id")
    public void clearRecentlyPlayed(User user) {
        List<RecentlyPlayed> userHistory = recentlyPlayedRepository.findByUserOrderByPlayedAtDesc(user);
        recentlyPlayedRepository.deleteAll(userHistory);
    }
}