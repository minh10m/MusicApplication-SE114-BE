package com.music.application.be.modules.favorite_playlist;

import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.user.User;
import com.music.application.be.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FavoritePlaylistService {

    @Autowired
    private FavoritePlaylistRepository favoritePlaylistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    // Add favorite playlist
    @CacheEvict(value = {"favoritePlaylists", "favoritePlaylistsSearch"}, key = "#userId")
    public FavoritePlaylistDTO addFavoritePlaylist(Long userId, Long playlistId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        FavoritePlaylist favoritePlaylist = FavoritePlaylist.builder()
                .user(user)
                .playlist(playlist)
                .addedAt(LocalDateTime.now())
                .build();

        FavoritePlaylist savedFavorite = favoritePlaylistRepository.save(favoritePlaylist);
        return mapToDTO(savedFavorite);
    }

    // Get favorite playlists
    @Cacheable(value = "favoritePlaylists", key = "#userId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FavoritePlaylistDTO> getFavoritePlaylists(Long userId, Pageable pageable) {
        return favoritePlaylistRepository.findByUserId(userId, pageable).map(this::mapToDTO);
    }

    // Search favorite playlists
    @Cacheable(value = "favoritePlaylistsSearch", key = "#userId + '-' + #query + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<FavoritePlaylistDTO> searchFavoritePlaylists(Long userId, String query, Pageable pageable) {
        return favoritePlaylistRepository.findByUserIdAndPlaylistNameContainingIgnoreCase(userId, query, pageable)
                .map(this::mapToDTO);
    }

    // Remove favorite playlist
    @CacheEvict(value = {"favoritePlaylists", "favoritePlaylistsSearch"}, key = "#favoritePlaylist.user.id")
    public void removeFavoritePlaylist(Long id) {
        FavoritePlaylist favoritePlaylist = favoritePlaylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite playlist not found"));
        favoritePlaylistRepository.delete(favoritePlaylist);
    }

    // Map entity to DTO
    private FavoritePlaylistDTO mapToDTO(FavoritePlaylist favoritePlaylist) {
        FavoritePlaylistDTO dto = new FavoritePlaylistDTO();
        dto.setId(favoritePlaylist.getId());
        dto.setUserId(favoritePlaylist.getUser().getId());
        dto.setPlaylistId(favoritePlaylist.getPlaylist().getId());
        dto.setAddedAt(favoritePlaylist.getAddedAt());
        return dto;
    }
}