package com.music.application.be.modules.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    public ResponseEntity<SongDTO> createSong(
            @RequestPart("song") SongDTO songDTO,
            @RequestPart("audio") MultipartFile audioFile,
            @RequestPart("thumbnail") MultipartFile thumbnailFile) throws Exception {
        return ResponseEntity.ok(songService.createSong(songDTO, audioFile, thumbnailFile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SongDTO>> getAllSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.getAllSongs(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(
            @PathVariable Long id,
            @RequestPart("song") SongDTO songDTO,
            @RequestPart(value = "audio", required = false) MultipartFile audioFile,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnailFile) throws Exception {
        return ResponseEntity.ok(songService.updateSong(id, songDTO, audioFile, thumbnailFile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SongDTO>> searchSongs(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.searchSongs(query, pageable));
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<SongDTO>> getSongsByArtist(
            @PathVariable Long artistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.getSongsByArtist(artistId, pageable));
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<Page<SongDTO>> getSongsByGenre(
            @PathVariable Long genreId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(songService.getSongsByGenre(genreId, pageable));
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<String> getSongThumbnail(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongThumbnail(id));
    }

    @GetMapping("/{id}/share")
    public ResponseEntity<String> shareSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.shareSong(id));
    }
}