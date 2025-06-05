package com.music.application.be.modules.song;

import com.music.application.be.modules.comment.CommentService;
import com.music.application.be.modules.comment.dto.CommentActionResponseDTO;
import com.music.application.be.modules.comment.dto.CommentResponseDTO;
import com.music.application.be.modules.comment.dto.CreateCommentDTO;
import com.music.application.be.modules.song.dto.CreateSongDTO;
import com.music.application.be.modules.song.dto.SongDTO;
import com.music.application.be.modules.song.dto.UpdateSongDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private CommentService commentService;

    // Only support MP3, WAV file
    @PostMapping
    public ResponseEntity<SongDTO> createSong(
            @RequestPart("song") CreateSongDTO createSongDTO,
            @RequestPart("audio") MultipartFile audioFile,
            @RequestPart("thumbnail") MultipartFile thumbnailFile) throws Exception {
        return ResponseEntity.ok(songService.createSong(createSongDTO, audioFile, thumbnailFile));
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
            @RequestPart("song") UpdateSongDTO updateSongDTO,
            @RequestPart(value = "audio", required = false) MultipartFile audioFile,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnailFile) throws Exception {
        return ResponseEntity.ok(songService.updateSong(id, updateSongDTO, audioFile, thumbnailFile));
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

    @GetMapping("/{id}/share")
    public ResponseEntity<String> shareSong(@PathVariable Long id) {
        return ResponseEntity.ok(songService.shareSong(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long id,
            @Valid @RequestBody CreateCommentDTO createCommentDTO) {
        return ResponseEntity.ok(commentService.createComment(id, createCommentDTO));
    }

    @PostMapping("/{songId}/comments/{commentId}/like")
    public ResponseEntity<CommentActionResponseDTO> likeComment(
            @PathVariable Long songId,
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(commentService.likeComment(commentId, userId));
    }

    @DeleteMapping("/{songId}/comments/{commentId}/unlike")
    public ResponseEntity<CommentActionResponseDTO> unlikeComment(
            @PathVariable Long songId,
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(commentService.unlikeComment(commentId, userId));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsBySongId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentsBySongId(id));
    }

    @GetMapping("/top")
    public ResponseEntity<Page<SongDTO>> getTopSongsByViewCount(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(songService.getTopSongsByViewCount(page, size));
    }
}