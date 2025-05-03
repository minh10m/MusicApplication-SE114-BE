package com.music.application.be.modules.downloaded_song;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.User;
import com.music.application.be.modules.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DownloadedSongService {

    private final DownloadedSongRepository downloadedSongRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Transactional
    public Optional<DownloadedSong> addDownloadedSong(Long userId, Long songId) {
        if (downloadedSongRepository.existsByUserIdAndSongId(userId, songId)) {
            return Optional.empty();
        }
        Optional<User> user = userRepository.findById(userId);
        Optional<Song> song = songRepository.findById(songId);
        if (user.isPresent() && song.isPresent()) {
            DownloadedSong download = DownloadedSong.builder()
                    .user(user.get())
                    .song(song.get())
                    .downloadedAt(LocalDateTime.now())
                    .build();
            return Optional.of(downloadedSongRepository.save(download));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean removeDownloadedSong(Long userId, Long songId) {
        return downloadedSongRepository.findByUserId(userId).stream()
                .filter(download -> download.getSong().getId().equals(songId))
                .findFirst()
                .map(download -> {
                    downloadedSongRepository.delete(download);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<DownloadedSong> getDownloadedSongs(Long userId) {
        return downloadedSongRepository.findByUserId(userId);
    }
}
