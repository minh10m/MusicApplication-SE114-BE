package com.music.application.be.modules.playlist;

import com.music.application.be.modules.genre.Genre;
import com.music.application.be.modules.genre.GenreRepository;
import com.music.application.be.modules.playlist.dto.PlaylistDTO;
import com.music.application.be.modules.playlist.dto.PlaylistRequestDTO;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.song_playlist.SongPlaylist;
import com.music.application.be.modules.song_playlist.SongPlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = {"playlists", "searchedPlaylists"}, allEntries = true)
    public PlaylistDTO createPlaylist(PlaylistRequestDTO playlistRequestDTO) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistRequestDTO.getName());
        playlist.setDescription(playlistRequestDTO.getDescription());
        playlist.setCreatedAt(LocalDateTime.now());

        // Liên kết genres
        if (playlistRequestDTO.getGenreIds() != null && !playlistRequestDTO.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(playlistRequestDTO.getGenreIds());
            if (genres.size() != playlistRequestDTO.getGenreIds().size()) {
                throw new EntityNotFoundException("One or more genres not found");
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
    @Cacheable(value = "playlists", key = "#id")
    public PlaylistDTO getPlaylistById(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + id));
        return mapToDTO(playlist);
    }

    // Read all with pagination
    @Cacheable(value = "playlists", key = "'all-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<PlaylistDTO> getAllPlaylists(Pageable pageable) {
        return playlistRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    @CachePut(value = "playlists", key = "#id")
    @CacheEvict(value = "searchedPlaylists", allEntries = true)
    public PlaylistDTO updatePlaylist(Long id, PlaylistRequestDTO playlistRequestDTO) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + id));

        playlist.setName(playlistRequestDTO.getName());
        playlist.setDescription(playlistRequestDTO.getDescription());

        // Cập nhật genres
        if (playlistRequestDTO.getGenreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(playlistRequestDTO.getGenreIds());
            if (genres.size() != playlistRequestDTO.getGenreIds().size()) {
                throw new EntityNotFoundException("One or more genres not found");
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
    @CacheEvict(value = {"playlists", "searchedPlaylists"}, allEntries = true)
    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + id));

        playlistRepository.delete(playlist);
    }

    // Search playlists
    public Page<PlaylistDTO> searchPlaylists(String query, Pageable pageable) {
        return playlistRepository.findByNameContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    // Share playlist
    @Cacheable(value = "playlists", key = "'share-' + #id")
    public String sharePlaylist(Long id) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + id));
        return "http://localhost:8080/api/playlists/" + id;
    }

    // Map entity to DTO
    private PlaylistDTO mapToDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        dto.setDescription(playlist.getDescription());
        dto.setCreatedAt(playlist.getCreatedAt());
        dto.setGenreIds(playlist.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        return dto;
    }
}