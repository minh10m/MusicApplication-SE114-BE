package com.music.application.be.modules.follow_artist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow-artist")
@RequiredArgsConstructor
public class FollowArtistController {

    private final FollowArtistService followArtistService;

    @PostMapping
    public ResponseEntity<FollowArtist> followArtist(@RequestParam Long userId, @RequestParam Long artistId) {
        return followArtistService.followArtist(userId, artistId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollowArtist(@RequestParam Long userId, @RequestParam Long artistId) {
        return followArtistService.unfollowArtist(userId, artistId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FollowArtist>> getFollowedArtists(@PathVariable Long userId) {
        return ResponseEntity.ok(followArtistService.getFollowedArtists(userId));
    }
}
