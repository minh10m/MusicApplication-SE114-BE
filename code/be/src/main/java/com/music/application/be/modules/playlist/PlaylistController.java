package com.music.application.be.modules.playlist;

import com.music.application.be.modules.playlist.dto.PagedResponseDTO;
import com.music.application.be.modules.playlist.dto.PlaylistDTO;
import com.music.application.be.modules.playlist.dto.PlaylistRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    // Create playlist for user (no genre required)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PlaylistDTO> createPlaylist(@Valid @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        return ResponseEntity.ok(playlistService.createPlaylist(playlistRequestDTO));
    }

    // Create playlist for admin with genres (auto-add songs)
    @PostMapping("/with-genres")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlaylistDTO> createPlaylistWithGenres(@Valid @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        return ResponseEntity.ok(playlistService.createPlaylistWithGenres(playlistRequestDTO));
    }

    // Get playlist by ID
    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.getPlaylistById(id));
    }

    // Get all playlists
    @GetMapping
    public ResponseEntity<PagedResponseDTO<PlaylistDTO>> getAllPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlaylistDTO> pageResult = playlistService.getAllPlaylists(pageable);
        PagedResponseDTO<PlaylistDTO> response = new PagedResponseDTO<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
        return ResponseEntity.ok(response);
    }

    // Update playlist
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PlaylistDTO> updatePlaylist(@PathVariable Long id, @Valid @RequestBody PlaylistRequestDTO playlistRequestDTO) {
        return ResponseEntity.ok(playlistService.updatePlaylist(id, playlistRequestDTO));
    }

    // Delete playlist
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok().build();
    }

    // Search playlists
    @GetMapping("/search")
    public ResponseEntity<PagedResponseDTO<PlaylistDTO>> searchPlaylists(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlaylistDTO> pageResult = playlistService.searchPlaylists(query, pageable);
        PagedResponseDTO<PlaylistDTO> response = new PagedResponseDTO<>(
                pageResult.getContent(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
        return ResponseEntity.ok(response);
    }

    // Share playlist
    @GetMapping("/{id}/share")
    public ResponseEntity<String> sharePlaylist(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.sharePlaylist(id));
    }
}