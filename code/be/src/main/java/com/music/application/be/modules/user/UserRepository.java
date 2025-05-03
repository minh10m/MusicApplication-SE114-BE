package com.music.application.be.modules.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"favoritePlaylists", "favoritePlaylists.songs"})
    Optional<User> findWithFavoritePlaylistsById(Long userId);

    // Lấy user kèm danh sách favorite songs
    @EntityGraph(attributePaths = {"favoriteSongs", "favoriteSongs.artist"})
    Optional<User> findWithFavoriteSongsById(Long userId);

    // Lấy user kèm danh sách followed artists
    @EntityGraph(attributePaths = {"followedArtists"})
    Optional<User> findWithFollowedArtistsById(Long userId);
}