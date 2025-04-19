package com.music.application.be.modules.song;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.album.AlbumRepository;
import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.genre.Genre;
import com.music.application.be.modules.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    @Transactional
    public Optional<Song> createSong(Song song, Long artistId, Long albumId, Long genreId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (artist.isEmpty()) {
            return Optional.empty();
        }

        song.setArtist(artist.get());

        if (albumId != null) {
            Optional<Album> album = albumRepository.findById(albumId);
            album.ifPresent(song::setAlbum);
        }

        if (genreId != null) {
            Optional<Genre> genre = genreRepository.findById(genreId);
            genre.ifPresent(song::setGenre);
        }

        return Optional.of(songRepository.save(song));
    }

    @Transactional
    public Optional<Song> updateSong(Long id, Song updatedSong) {
        return songRepository.findById(id)
                .map(existingSong -> {
                    existingSong.setTitle(updatedSong.getTitle());
                    existingSong.setDuration(updatedSong.getDuration());
                    existingSong.setAudioUrl(updatedSong.getAudioUrl());
                    existingSong.setThumbnail(updatedSong.getThumbnail());
                    existingSong.setLyrics(updatedSong.getLyrics());
                    existingSong.setReleaseDate(updatedSong.getReleaseDate());
                    return songRepository.save(existingSong);
                });
    }

    @Transactional
    public boolean deleteSong(Long id) {
        return songRepository.findById(id)
                .map(song -> {
                    songRepository.delete(song);
                    return true;
                })
                .orElse(false);
    }
}
