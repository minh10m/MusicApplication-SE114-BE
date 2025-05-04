package com.music.application.be.modules.follow_artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow-artists")
public class FollowArtistController {

    @Autowired
    private FollowArtistService followArtistService;

    // Follow artist
    @PostMapping
    public ResponseEntity<FollowArtistDTO> followArtist(
            @RequestParam Long userId,
            @RequestParam Long artistId) {
        return ResponseEntity.ok(followArtistService.followArtist(userId, artistId));
    }

    // Unfollow artist
    @DeleteMapping
    public ResponseEntity<Void> unfollowArtist(
            @RequestParam Long userId,
            @RequestParam Long artistId) {
        followArtistService.unfollowArtist(userId, artistId);
        return ResponseEntity.ok().build();
    }

    // Get followed artists with sorting
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<FollowArtistDTO>> getFollowedArtists(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "followedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(followArtistService.getFollowedArtists(userId, pageable));
    }

    // Search followed artists with sorting
    @GetMapping("/user/{userId}/search")
    public ResponseEntity<Page<FollowArtistDTO>> searchFollowedArtists(
            @PathVariable Long userId,
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "followedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(followArtistService.searchFollowedArtists(userId, query, pageable));
    }
}
