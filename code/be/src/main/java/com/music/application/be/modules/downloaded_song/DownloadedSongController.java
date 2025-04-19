package com.music.application.be.modules.downloaded_song;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/downloaded-songs")
@RequiredArgsConstructor
public class DownloadedSongController {

    private final DownloadedSongService downloadedSongService;

    @PostMapping
    public ResponseEntity<DownloadedSong> addDownloadedSong(@RequestParam Long userId, @RequestParam Long songId) {
        return downloadedSongService.addDownloadedSong(userId, songId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> removeDownloadedSong(@RequestParam Long userId, @RequestParam Long songId) {
        return downloadedSongService.removeDownloadedSong(userId, songId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DownloadedSong>> getDownloadedSongs(@PathVariable Long userId) {
        return ResponseEntity.ok(downloadedSongService.getDownloadedSongs(userId));
    }
}
