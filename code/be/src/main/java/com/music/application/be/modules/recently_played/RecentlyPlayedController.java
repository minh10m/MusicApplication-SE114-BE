package com.music.application.be.modules.recently_played;

import com.music.application.be.modules.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/recently-played")
@RequiredArgsConstructor
public class RecentlyPlayedController {

    private final RecentlyPlayedService recentlyPlayedService;

    @PostMapping
    public ResponseEntity<?> addRecentlyPlayed(@RequestBody RecentlyPlayedRequest request) {
        try {
            RecentlyPlayed recentlyPlayed = recentlyPlayedService.addRecentlyPlayed(request.getUser(), request.getSong());
            return ResponseEntity.ok(recentlyPlayed);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding to recently played: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getRecentlyPlayed(@PathVariable User user) {
        try {
            List<RecentlyPlayed> recentlyPlayedList = recentlyPlayedService.getRecentlyPlayedByUser(user);
            return ResponseEntity.ok(recentlyPlayedList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching recently played: " + e.getMessage());
        }
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearRecentlyPlayed(@PathVariable User user) {
        try {
            recentlyPlayedService.clearRecentlyPlayed(user);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error clearing recently played: " + e.getMessage());
        }
    }
}
