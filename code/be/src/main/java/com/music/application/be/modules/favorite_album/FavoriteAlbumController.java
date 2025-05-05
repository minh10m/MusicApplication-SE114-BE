package com.music.application.be.modules.favorite_album;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite-albums")
public class FavoriteAlbumController {

    @Autowired
    private FavoriteAlbumService favoriteAlbumService;

    // Add favorite album
    @PostMapping
    public ResponseEntity<FavoriteAlbumDTO> addFavoriteAlbum(
            @RequestParam Long userId,
            @RequestParam Long albumId) {
        return ResponseEntity.ok(favoriteAlbumService.addFavoriteAlbum(userId, albumId));
    }

    // Get favorite albums
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<FavoriteAlbumDTO>> getFavoriteAlbums(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "addedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(favoriteAlbumService.getFavoriteAlbums(userId, pageable));
    }

    // Search favorite albums
    @GetMapping("/user/{userId}/search")
    public ResponseEntity<Page<FavoriteAlbumDTO>> searchFavoriteAlbums(
            @PathVariable Long userId,
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "addedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(favoriteAlbumService.searchFavoriteAlbums(userId, query, pageable));
    }

    // Remove favorite album
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavoriteAlbum(@PathVariable Long id) {
        favoriteAlbumService.removeFavoriteAlbum(id);
        return ResponseEntity.ok().build();
    }
}