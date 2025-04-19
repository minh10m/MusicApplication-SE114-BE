package com.music.application.be.modules.favorite_song;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite-songs")
@RequiredArgsConstructor
public class FavoriteSongController {

    private final FavoriteSongService favoriteSongService;

    @PostMapping
    public ResponseEntity<FavoriteSong> addFavoriteSong(@RequestParam Long userId, @RequestParam Long songId) {
        return favoriteSongService.addFavoriteSong(userId, songId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavoriteSong(@RequestParam Long userId, @RequestParam Long songId) {
        return favoriteSongService.removeFavoriteSong(userId, songId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteSong>> getFavoriteSongs(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteSongService.getFavoriteSongs(userId));
    }
}
