package com.music.application.be.modules.user;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Lấy thông tin user
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    // Cập nhật thông tin user
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestBody UserDTO userDTO
    ) {
        User updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/{userId}/favorite-playlists")
    public ResponseEntity<Set<Playlist>> getFavoritePlaylists(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserFavoritePlaylists(userId));
    }

    @PostMapping("/{userId}/favorite-playlists/{playlistId}")
    public ResponseEntity<String> addFavoritePlaylist(
            @PathVariable Long userId,
            @PathVariable Long playlistId) {
        return ResponseEntity.ok(userService.addFavoritePlaylist(userId, playlistId));
    }

    // ====== SONGS ======
    @GetMapping("/{userId}/favorite-songs")
    public ResponseEntity<Set<Song>> getFavoriteSongs(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserFavoriteSongs(userId));
    }

    @PostMapping("/{userId}/favorite-songs/{songId}")
    public ResponseEntity<String> addFavoriteSong(
            @PathVariable Long userId,
            @PathVariable Long songId) {
        return ResponseEntity.ok(userService.addFavoriteSong(userId, songId));
    }

    // ====== ARTISTS ======
    @GetMapping("/{userId}/followed-artists")
    public ResponseEntity<Set<Artist>> getFollowedArtists(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserFollowedArtists(userId));
    }

    @PostMapping("/{userId}/followed-artists/{artistId}")
    public ResponseEntity<String> followArtist(
            @PathVariable Long userId,
            @PathVariable Long artistId) {
        return ResponseEntity.ok(userService.followArtist(userId, artistId));
    }

    @GetMapping("/{userId}/favorite-playlists/count")
    public ResponseEntity<Map<String, Integer>> countFavoritePlaylists(
            @PathVariable Long userId) {
        int count = userService.countFavoritePlaylists(userId);
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }

    // ====== Đếm số lượng favorite songs ======
    @GetMapping("/{userId}/favorite-songs/count")
    public ResponseEntity<Map<String, Integer>> countFavoriteSongs(
            @PathVariable Long userId) {
        int count = userService.countFavoriteSongs(userId);
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }

    // ====== Đếm số lượng followed artists ======
    @GetMapping("/{userId}/followed-artists/count")
    public ResponseEntity<Map<String, Integer>> countFollowedArtists(
            @PathVariable Long userId) {
        int count = userService.countFollowedArtists(userId);
        return ResponseEntity.ok(Collections.singletonMap("count", count));
    }
}
