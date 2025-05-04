package com.music.application.be.modules.downloaded_song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/downloaded-songs")
public class DownloadedSongController {

    @Autowired
    private DownloadedSongService downloadedSongService;

    // Add downloaded song
    @PostMapping
    public ResponseEntity<DownloadedSongDTO> addDownloadedSong(
            @RequestParam Long userId,
            @RequestParam Long songId) {
        return ResponseEntity.ok(downloadedSongService.addDownloadedSong(userId, songId));
    }

    // Get downloaded songs
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<DownloadedSongDTO>> getDownloadedSongs(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(downloadedSongService.getDownloadedSongs(userId, pageable));
    }

    // Search downloaded songs
    @GetMapping("/user/{userId}/search")
    public ResponseEntity<Page<DownloadedSongDTO>> searchDownloadedSongs(
            @PathVariable Long userId,
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(downloadedSongService.searchDownloadedSongs(userId, query, pageable));
    }

    // Remove downloaded song
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDownloadedSong(@PathVariable Long id) {
        downloadedSongService.removeDownloadedSong(id);
        return ResponseEntity.ok().build();
    }
}
