package com.music.application.be.modules.follow_artist;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowArtistService {

    private final FollowArtistRepository followArtistRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public Optional<FollowArtist> followArtist(Long userId, Long artistId) {
        if (followArtistRepository.existsByUserIdAndArtistId(userId, artistId)) {
            return Optional.empty();
        }
        return artistRepository.findById(artistId)
                .map(artist -> {
                    FollowArtist follow = new FollowArtist();
                    follow.setUserId(userId);
                    follow.setArtist(artist);
                    follow.setFollowedAt(LocalDateTime.now());
                    artist.setFollowerCount(artist.getFollowerCount() + 1);
                    artistRepository.save(artist);
                    return followArtistRepository.save(follow);
                });
    }

    @Transactional
    public boolean unfollowArtist(Long userId, Long artistId) {
        return followArtistRepository.findByUserId(userId).stream()
                .filter(follow -> follow.getArtist().getId().equals(artistId))
                .findFirst()
                .map(follow -> {
                    Artist artist = follow.getArtist();
                    artist.setFollowerCount(Math.max(0, artist.getFollowerCount() - 1));
                    artistRepository.save(artist);
                    followArtistRepository.delete(follow);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<FollowArtist> getFollowedArtists(Long userId) {
        return followArtistRepository.findByUserId(userId);
    }
}
