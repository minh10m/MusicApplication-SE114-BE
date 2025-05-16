package com.music.application.be.modules.queue;


import com.music.application.be.modules.queue.dto.QueueRequest;
import com.music.application.be.modules.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @PostMapping
    public ResponseEntity<?> addToQueue(@RequestBody QueueRequest request) {
        try {
            Queue queue = queueService.addToQueue(request.getUser(), request.getSong(), request.getPosition());
            return ResponseEntity.ok(queue);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding to queue: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserQueue(@PathVariable User user) {
        try {
            List<Queue> queueList = queueService.getUserQueue(user);
            return ResponseEntity.ok(queueList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching queue: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeFromQueue(@PathVariable Long id) {
        try {
            queueService.removeFromQueue(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Queue item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error removing from queue: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/position")
    public ResponseEntity<?> updatePosition(@PathVariable Long id, @RequestParam Integer position) {
        try {
            queueService.updatePosition(id, position);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Queue item not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating position: " + e.getMessage());
        }
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearUserQueue(@PathVariable User user) {
        try {
            queueService.clearUserQueue(user);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error clearing queue: " + e.getMessage());
        }
    }
}


