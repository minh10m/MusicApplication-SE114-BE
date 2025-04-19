package com.music.application.be.modules.song_playlist;

import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongPlaylistService {

    private final SongPlaylistRepository songPlaylistRepository;
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;

    @Transactional
    public Optional<SongPlaylist> addSongToPlaylist(Long songId, Long playlistId, Integer position) {
        Optional<Song> song = songRepository.findById(songId);
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if (song.isPresent() && playlist.isPresent()) {
            SongPlaylist songPlaylist = new SongPlaylist();
            songPlaylist.setSong(song.get());
            songPlaylist.setPlaylist(playlist.get());
            songPlaylist.setPosition(position != null ? position : 0);
            return Optional.of(songPlaylistRepository.save(songPlaylist));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean removeSongFromPlaylist(Long songPlaylistId) {
        return songPlaylistRepository.findById(songPlaylistId)
                .map(songPlaylist -> {
                    songPlaylistRepository.delete(songPlaylist);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<SongPlaylist> updateSongPosition(Long songPlaylistId, Integer newPosition) {
        return songPlaylistRepository.findById(songPlaylistId)
                .map(songPlaylist -> {
                    songPlaylist.setPosition(newPosition);
                    return songPlaylistRepository.save(songPlaylist);
                });
    }
}
