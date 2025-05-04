package com.music.application.be.modules.favorite_song;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.MyUser;
import com.music.application.be.modules.user.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FavoriteSongService {

    @Autowired
    private FavoriteSongRepository favoriteSongRepository;

    @Autowired
    private MyUserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    // Add favorite song
    public FavoriteSongDTO addFavoriteSong(Long userId, Long songId) {
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        FavoriteSong favoriteSong = FavoriteSong.builder()
                .user(user)
                .song(song)
                .addedAt(LocalDateTime.now())
                .build();

        FavoriteSong savedFavorite = favoriteSongRepository.save(favoriteSong);
        return mapToDTO(savedFavorite);
    }

    // Get favorite songs
    public Page<FavoriteSongDTO> getFavoriteSongs(Long userId, Pageable pageable) {
        return favoriteSongRepository.findByUserId(userId, pageable).map(this::mapToDTO);
    }

    // Search favorite songs
    public Page<FavoriteSongDTO> searchFavoriteSongs(Long userId, String query, Pageable pageable) {
        return favoriteSongRepository.findByUserIdAndSongTitleContainingIgnoreCase(userId, query, pageable)
                .map(this::mapToDTO);
    }

    // Remove favorite song
    public void removeFavoriteSong(Long id) {
        FavoriteSong favoriteSong = favoriteSongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite song not found"));
        favoriteSongRepository.delete(favoriteSong);
    }

    // Map entity to DTO
    private FavoriteSongDTO mapToDTO(FavoriteSong favoriteSong) {
        FavoriteSongDTO dto = new FavoriteSongDTO();
        dto.setId(favoriteSong.getId());
        dto.setUserId(favoriteSong.getUser().getId());
        dto.setSongId(favoriteSong.getSong().getId());
        dto.setAddedAt(favoriteSong.getAddedAt());
        return dto;
    }
}
