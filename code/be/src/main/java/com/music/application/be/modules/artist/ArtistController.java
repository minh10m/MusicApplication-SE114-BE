package com.music.application.be.modules.artist;

    import com.music.application.be.modules.artist.dto.ArtistResponseDTO;
    import com.music.application.be.modules.artist.dto.CreateArtistDTO;
    import com.music.application.be.modules.artist.dto.UpdateArtistDTO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import jakarta.validation.Valid;

    @RestController
    @RequestMapping("/api/artists")
    public class ArtistController {

        @Autowired
        private ArtistService artistService;

        @PostMapping
        public ResponseEntity<ArtistResponseDTO> createArtist(@Valid @RequestBody CreateArtistDTO createArtistDTO) {
            return ResponseEntity.ok(artistService.createArtist(createArtistDTO));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) {
            return ResponseEntity.ok(artistService.getArtistById(id));
        }

        @GetMapping
        public ResponseEntity<Page<ArtistResponseDTO>> getAllArtists(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "20") int size) {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(artistService.getAllArtists(pageable));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ArtistResponseDTO> updateArtist(
                @PathVariable Long id,
                @Valid @RequestBody UpdateArtistDTO updateArtistDTO) {
            return ResponseEntity.ok(artistService.updateArtist(id, updateArtistDTO));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
            artistService.deleteArtist(id);
            return ResponseEntity.ok().build();
        }

        @GetMapping("/search")
        public ResponseEntity<Page<ArtistResponseDTO>> searchArtists(
                @RequestParam String query,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "20") int size) {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(artistService.searchArtists(query, pageable));
        }

        @GetMapping("/{id}/share")
        public ResponseEntity<String> shareArtist(@PathVariable Long id) {
            return ResponseEntity.ok(artistService.shareArtist(id));
        }
    }