package com.music.application.be.modules.playlist;

import com.music.application.be.modules.song_playlist.SongPlaylist;
import com.music.application.be.modules.song_playlist.SongPlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongPlaylistRepository songPlaylistRepository;

    @Transactional(readOnly = true)
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Playlist> getPlaylistById(Long id) {
        return playlistRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Playlist> getPlaylistsByOwner(Long ownerId) {
        return playlistRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public Playlist createPlaylist(Playlist playlist, Long ownerId) {
        playlist.setOwnerId(ownerId);
        return playlistRepository.save(playlist);
    }

    @Transactional
    public Optional<Playlist> updatePlaylist(Long id, Playlist updatedPlaylist) {
        return playlistRepository.findById(id)
                .map(existingPlaylist -> {
                    existingPlaylist.setName(updatedPlaylist.getName());
                    existingPlaylist.setDescription(updatedPlaylist.getDescription());
                    existingPlaylist.setType(updatedPlaylist.getType());
                    return playlistRepository.save(existingPlaylist);
                });
    }

    @Transactional
    public boolean deletePlaylist(Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlistRepository.delete(playlist);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<SongPlaylist> getSongsInPlaylist(Long playlistId) {
        return songPlaylistRepository.findByPlaylistId(playlistId);
    }
}
