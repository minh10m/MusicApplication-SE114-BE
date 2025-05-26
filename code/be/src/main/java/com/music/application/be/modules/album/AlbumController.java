package com.music.application.be.modules.album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumDTO> createAlbum(
            @RequestPart("album") AlbumDTO albumDTO,
            @RequestPart("coverImage") MultipartFile coverImageFile) throws Exception {
        AlbumDTO result = albumService.createAlbum(albumDTO, coverImageFile);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AlbumDTO>> getAllAlbums(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(albumService.getAllAlbums(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(
            @PathVariable Long id,
            @RequestPart("album") AlbumDTO albumDTO,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImageFile) throws Exception {
        AlbumDTO result = albumService.updateAlbum(id, albumDTO, coverImageFile);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AlbumDTO>> searchAlbums(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(albumService.searchAlbums(query, pageable));
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<AlbumDTO>> getAlbumsByArtist(
            @PathVariable Long artistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(albumService.getAlbumsByArtist(artistId, pageable));
    }

    @GetMapping("/{id}/share")
    public ResponseEntity<String> shareAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.shareAlbum(id));
    }
}