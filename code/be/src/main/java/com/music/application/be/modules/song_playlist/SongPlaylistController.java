package com.music.application.be.modules.song_playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song-playlists")
public class SongPlaylistController {

    @Autowired
    private SongPlaylistService songPlaylistService;

    // Add song to playlist
    @PostMapping
    public ResponseEntity<SongPlaylistDTO> addSongToPlaylist(@RequestBody SongPlaylistDTO songPlaylistDTO) {
        return ResponseEntity.ok(songPlaylistService.addSongToPlaylist(songPlaylistDTO));
    }

    // Update addedAt
    @PutMapping("/{id}")
    public ResponseEntity<SongPlaylistDTO> updateSongPlaylist(
            @PathVariable Long id,
            @RequestBody SongPlaylistDTO songPlaylistDTO) {
        return ResponseEntity.ok(songPlaylistService.updateSongPlaylist(id, songPlaylistDTO));
    }

    // Remove song from playlist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long id) {
        songPlaylistService.removeSongFromPlaylist(id);
        return ResponseEntity.ok().build();
    }
}
