package com.music.application.be.modules.favorite_song;

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
public class FavoriteSongService {

    private final FavoriteSongRepository favoriteSongRepository;
    private final SongRepository songRepository;
    private final MyUserRepository userRepository;

    @Transactional
    public Optional<FavoriteSong> addFavoriteSong(Long userId, Long songId) {
        if (favoriteSongRepository.existsByUserIdAndSongId(userId, songId)) {
            return Optional.empty();
        }
        Optional<MyUser> user = userRepository.findById(userId);
        Optional<Song> song = songRepository.findById(songId);
        if (user.isPresent() && song.isPresent()) {
            FavoriteSong favorite = FavoriteSong.builder()
                    .user(user.get())
                    .song(song.get())
                    .addedAt(LocalDateTime.now())
                    .build();
            return Optional.of(favoriteSongRepository.save(favorite));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean removeFavoriteSong(Long userId, Long songId) {
        return favoriteSongRepository.findByUserId(userId).stream()
                .filter(favorite -> favorite.getSong().getId().equals(songId))
                .findFirst()
                .map(favorite -> {
                    favoriteSongRepository.delete(favorite);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<FavoriteSong> getFavoriteSongs(Long userId) {
        return favoriteSongRepository.findByUserId(userId);
    }
}
