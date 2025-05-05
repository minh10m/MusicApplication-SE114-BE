package com.music.application.be.modules.user;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    public User updateUser(Long userId, UserDTO userDTO) {
        User user = getUserById(userId);
        user.setUsername(userDTO.getUsername());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public Set<Playlist> getUserFavoritePlaylists(Long userId) {
        User user = userRepository.findWithFavoritePlaylistsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoritePlaylists();
    }

    public String addFavoritePlaylist(Long userId, Long playlistId) {
        User user = userRepository.findWithFavoritePlaylistsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        // Kiểm tra nếu playlist đã có trong danh sách yêu thích
        if (user.getFavoritePlaylists().contains(playlist)) {
            return "Playlist already in favorites. Total: " + user.getFavoritePlaylists().size();
        }

        user.getFavoritePlaylists().add(playlist);
        userRepository.save(user);

        return "Added playlist to favorites. Total: " + user.getFavoritePlaylists().size();
    }

    public Set<Song> getUserFavoriteSongs(Long userId) {
        User user = userRepository.findWithFavoriteSongsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteSongs();
    }

    public String addFavoriteSong(Long userId, Long songId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        user.getFavoriteSongs().add(song);
        userRepository.save(user);

        return "Added song to favorites. Total: " + user.getFavoriteSongs().size();
    }

    public Set<Artist> getUserFollowedArtists(Long userId) {
        User user = userRepository.findWithFollowedArtistsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowedArtists();
    }

    public String followArtist(Long userId, Long artistId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        user.getFollowedArtists().add(artist);
        userRepository.save(user);

        return "Followed artist. Total: " + user.getFollowedArtists().size();
    }

    public int countFavoritePlaylists(Long userId) {
        User user = userRepository.findWithFavoritePlaylistsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoritePlaylists().size();
    }

    // ====== Số lượng favorite songs ======
    public int countFavoriteSongs(Long userId) {
        User user = userRepository.findWithFavoriteSongsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteSongs().size();
    }

    // ====== Số lượng followed artists ======
    public int countFollowedArtists(Long userId) {
        User user = userRepository.findWithFollowedArtistsById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowedArtists().size();
    }
}
