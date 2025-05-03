package com.music.application.be.modules.song_playlist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/song-playlist")
@RequiredArgsConstructor
public class SongPlaylistController {

    private final SongPlaylistService songPlaylistService;

    @PostMapping
    public ResponseEntity<SongPlaylist> addSongToPlaylist(@RequestParam Long songId,
                                                          @RequestParam Long playlistId,
                                                          @RequestParam(required = false) Integer position) {
        return songPlaylistService.addSongToPlaylist(songId, playlistId, position)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long id) {
        return songPlaylistService.removeSongFromPlaylist(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/position")
    public ResponseEntity<SongPlaylist> updateSongPosition(@PathVariable Long id, @RequestParam Integer newPosition) {
        return songPlaylistService.updateSongPosition(id, newPosition)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
