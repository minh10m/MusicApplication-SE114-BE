package com.music.application.be.modules.genre;

import com.music.application.be.modules.genre.dto.GenreDTO;
import com.music.application.be.modules.genre.dto.GenreRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    // Create genre
    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@Valid @RequestBody GenreRequestDTO genreRequestDTO) {
        return ResponseEntity.ok(genreService.createGenre(genreRequestDTO));
    }

    // Get genre by ID
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    // Get all genres
    @GetMapping
    public ResponseEntity<Page<GenreDTO>> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(genreService.getAllGenres(pageable));
    }

    // Update genre
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreRequestDTO genreRequestDTO) {
        return ResponseEntity.ok(genreService.updateGenre(id, genreRequestDTO));
    }

    // Delete genre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok().build();
    }

    // Search genres
    @GetMapping("/search")
    public ResponseEntity<Page<GenreDTO>> searchGenres(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(genreService.searchGenres(query, pageable));
    }
}