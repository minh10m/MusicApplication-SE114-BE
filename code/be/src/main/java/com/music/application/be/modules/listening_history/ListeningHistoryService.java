package com.music.application.be.modules.listening_history;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.MyUser;
import com.music.application.be.modules.user.MyUserRepository;
import lombok.RequiredArgsConstructor;
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
    private final MyUserRepository userRepository;

    @Transactional
    public Optional<ListeningHistory> addListeningHistory(Long userId, Long songId, Integer durationPlayed) {
        Optional<MyUser> user = userRepository.findById(userId);
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
    public List<ListeningHistory> getListeningHistory(Long userId) {
        return listeningHistoryRepository.findByUserId(userId);
    }
}
