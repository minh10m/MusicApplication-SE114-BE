package com.music.application.be.modules.listening_history;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.User;
import com.music.application.be.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListeningHistoryService {

    private final ListeningHistoryRepository listeningHistoryRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Transactional
    @CacheEvict(value = "listeningHistory", key = "#userId")
    public Optional<ListeningHistory> addListeningHistory(Long userId, Long songId, Integer durationPlayed) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Song> song = songRepository.findById(songId);
        if (user.isPresent() && song.isPresent()) {
            ListeningHistory history = ListeningHistory.builder()
                    .user(user.get())
                    .song(song.get())
                    .playedAt(LocalDateTime.now())
                    .durationPlayed(durationPlayed)
                    .build();
            return Optional.of(listeningHistoryRepository.save(history));
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "listeningHistory", key = "#userId")
    public List<ListeningHistory> getListeningHistory(Long userId) {
        return listeningHistoryRepository.findByUserId(userId);
    }
}