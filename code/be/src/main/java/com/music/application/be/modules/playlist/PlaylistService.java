package com.music.application.be.modules.playlist;

import com.music.application.be.modules.genre.Genre;
import com.music.application.be.modules.genre.GenreRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.song_playlist.SongPlaylist;
import com.music.application.be.modules.song_playlist.SongPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongPlaylistRepository songPlaylistRepository;

    // Create
    public PlaylistDTO createPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistDTO.getName());
        playlist.setDescription(playlistDTO.getDescription());
        playlist.setCreatedAt(LocalDateTime.now());

        // Liên kết genres
        if (playlistDTO.getGenreIds() != null && !playlistDTO.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(playlistDTO.getGenreIds());
            if (genres.size() != playlistDTO.getGenreIds().size()) {
                throw new RuntimeException("One or more genres not found");
            }
            playlist.setGenres(genres);
        }

        Playlist savedPlaylist = playlistRepository.save(playlist);

        // Tự động thêm các bài hát có genre trùng
        if (!playlist.getGenres().isEmpty()) {
            List<Song> matchingSongs = songRepository.findByGenresIn(playlist.getGenres());
            for (Song song : matchingSongs) {
                SongPlaylist songPlaylist = new SongPlaylist();
                songPlaylist.setSong(song);
                songPlaylist.setPlaylist(savedPlaylist);
                songPlaylist.setAddedAt(LocalDateTime.now());
                songPlaylistRepository.save(songPlaylist);
            }
        }

        return mapToDTO(savedPlaylist);
    }

    // Read by ID
    public PlaylistDTO getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        return mapToDTO(playlist);
    }

    // Read all with pagination
    public Page<PlaylistDTO> getAllPlaylists(Pageable pageable) {
        return playlistRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    public PlaylistDTO updatePlaylist(Long id, PlaylistDTO playlistDTO) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.setName(playlistDTO.getName());
        playlist.setDescription(playlistDTO.getDescription());

        // Cập nhật genres
        if (playlistDTO.getGenreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(playlistDTO.getGenreIds());
            if (genres.size() != playlistDTO.getGenreIds().size()) {
                throw new RuntimeException("One or more genres not found");
            }
            playlist.setGenres(genres);
        } else {
            playlist.setGenres(null);
        }

        // Xóa các bài hát cũ
        List<SongPlaylist> existingSongs = songPlaylistRepository.findByPlaylistIdOrderByAddedAtDesc(id);
        songPlaylistRepository.deleteAll(existingSongs);

        // Tự động thêm các bài hát có genre trùng
        if (playlist.getGenres() != null && !playlist.getGenres().isEmpty()) {
            List<Song> matchingSongs = songRepository.findByGenresIn(playlist.getGenres());
            for (Song song : matchingSongs) {
                SongPlaylist songPlaylist = new SongPlaylist();
                songPlaylist.setSong(song);
                songPlaylist.setPlaylist(playlist);
                songPlaylist.setAddedAt(LocalDateTime.now());
                songPlaylistRepository.save(songPlaylist);
            }
        }

        Playlist updatedPlaylist = playlistRepository.save(playlist);
        return mapToDTO(updatedPlaylist);
    }

    // Delete
    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlistRepository.delete(playlist);
    }

    // Search playlists
    public Page<PlaylistDTO> searchPlaylists(String query, Pageable pageable) {
        return playlistRepository.findByNameContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    // Share playlist
    public String sharePlaylist(Long id) {
        return "https://musicapp.com/playlist/" + id;
    }

    // Map entity to DTO
    private PlaylistDTO mapToDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setDescription(playlist.getDescription());
        dto.setCreatedAt(playlist.getCreatedAt());
        dto.setGenreIds(playlist.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        List<SongPlaylist> songPlaylists = songPlaylistRepository.findByPlaylistIdOrderByAddedAtDesc(playlist.getId());
        return dto;
    }
}