package com.music.application.be.modules.song_playlist;

import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SongPlaylistService {

    @Autowired
    private SongPlaylistRepository songPlaylistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    // Add song to playlist
    @CacheEvict(value = "songPlaylists", key = "#songPlaylistDTO.playlistId")
    public SongPlaylistDTO addSongToPlaylist(SongPlaylistDTO songPlaylistDTO) {
        Song song = songRepository.findById(songPlaylistDTO.getSongId())
                .orElseThrow(() -> new RuntimeException("Song not found"));
        Playlist playlist = playlistRepository.findById(songPlaylistDTO.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        // Kiểm tra xem bài hát đã có trong playlist chưa
        boolean exists = songPlaylistRepository.findByPlaylistIdOrderByAddedAtDesc(playlist.getId())
                .stream()
                .anyMatch(sp -> sp.getSong().getId().equals(song.getId()));
        if (exists) {
            throw new RuntimeException("Song already exists in playlist");
        }

        SongPlaylist songPlaylist = new SongPlaylist();
        songPlaylist.setSong(song);
        songPlaylist.setPlaylist(playlist);
        songPlaylist.setAddedAt(LocalDateTime.now());

        SongPlaylist savedSongPlaylist = songPlaylistRepository.save(songPlaylist);
        return mapToDTO(savedSongPlaylist);
    }

    // Update addedAt
    @CachePut(value = "songPlaylists", key = "#id")
    public SongPlaylistDTO updateSongPlaylist(Long id, SongPlaylistDTO songPlaylistDTO) {
        SongPlaylist songPlaylist = songPlaylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SongPlaylist not found"));

        if (songPlaylistDTO.getAddedAt() != null) {
            songPlaylist.setAddedAt(songPlaylistDTO.getAddedAt());
        }

        SongPlaylist updatedSongPlaylist = songPlaylistRepository.save(songPlaylist);
        return mapToDTO(updatedSongPlaylist);
    }

    // Remove song from playlist
    @CacheEvict(value = "songPlaylists", key = "#id")
    public void removeSongFromPlaylist(Long id) {
        SongPlaylist songPlaylist = songPlaylistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SongPlaylist not found"));
        songPlaylistRepository.delete(songPlaylist);
    }

    // Map entity to DTO
    private SongPlaylistDTO mapToDTO(SongPlaylist songPlaylist) {
        SongPlaylistDTO dto = new SongPlaylistDTO();
        dto.setId(songPlaylist.getId());
        dto.setSongId(songPlaylist.getSong().getId());
        dto.setPlaylistId(songPlaylist.getPlaylist().getId());
        dto.setAddedAt(songPlaylist.getAddedAt());
        return dto;
    }
}