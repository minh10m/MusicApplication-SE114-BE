package com.music.application.be.modules.artist;

import com.music.application.be.modules.album.AlbumDTO;
import com.music.application.be.modules.song.SongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // Create artist
    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.createArtist(artistDTO));
    }

    // Get artist by ID
    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    // Get all artists
    @GetMapping
    public ResponseEntity<Page<ArtistDTO>> getAllArtists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(artistService.getAllArtists(pageable));
    }

    // Update artist
    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(@PathVariable Long id, @RequestBody ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.updateArtist(id, artistDTO));
    }

    // Delete artist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok().build();
    }

    // Search artists
    @GetMapping("/search")
    public ResponseEntity<Page<ArtistDTO>> searchArtists(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(artistService.searchArtists(query, pageable));
    }

    // Share artist
    @GetMapping("/{id}/share")
    public ResponseEntity<String> shareArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.shareArtist(id));
    }
}
