package com.music.application.be.modules.listening_history;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listening-history")
@RequiredArgsConstructor
public class ListeningHistoryController {

    private final ListeningHistoryService listeningHistoryService;

    @PostMapping
    public ResponseEntity<ListeningHistory> addListeningHistory(@RequestParam Long userId,
                                                                @RequestParam Long songId,
                                                                @RequestParam Integer durationPlayed) {
        return listeningHistoryService.addListeningHistory(userId, songId, durationPlayed)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListeningHistory>> getListeningHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(listeningHistoryService.getListeningHistory(userId));
    }
}