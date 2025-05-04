package com.music.application.be.modules.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    // Create song
    @PostMapping
    public ResponseEntity<SongDTO> createSong(@RequestBody SongDTO songDTO) {
        return ResponseEntity.ok(songService.createSong(songDTO));
    }

    // Get song by ID
    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    // Get all songs
    @GetMapping
    public ResponseEntity<Page<SongDTO>> getAllSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.getAllSongs(pageable));
    }

    // Update song
    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Long id, @RequestBody SongDTO songDTO) {
        return ResponseEntity.ok(songService.updateSong(id, songDTO));
    }

    // Delete song
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }

    // Search songs
    @GetMapping("/search")
    public ResponseEntity<Page<SongDTO>> searchSongs(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.searchSongs(query, pageable));
    }

    // Get songs by artist
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<SongDTO>> getSongsByArtist(
            @PathVariable Long artistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.getSongsByArtist(artistId, pageable));
    }

    // Get songs by genre
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<Page<SongDTO>> getSongsByGenre(
            @PathVariable Long genreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.getSongsByGenre(genreId, pageable));
    }

    // Get song thumbnail
    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<String> getSongThumbnail(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongThumbnail(id));
    }

    // Share song
    @GetMapping("/{id}/share")
    public ResponseEntity<String> shareSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.shareSong(id));
    }
}