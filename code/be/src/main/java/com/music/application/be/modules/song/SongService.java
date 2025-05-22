package com.music.application.be.modules.song;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.album.AlbumRepository;
import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.genre.Genre;
import com.music.application.be.modules.genre.GenreRepository;
import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.song_playlist.SongPlaylist;
import com.music.application.be.modules.song_playlist.SongPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongPlaylistRepository songPlaylistRepository;

    // Create Song & Add to Playlists with same genres automatically
    @CacheEvict(value = {"songs", "songsByGenre", "songsByArtist", "songPlaylists"}, allEntries = true)
    public SongDTO createSong(SongDTO songDTO) {
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setDuration(songDTO.getDuration());
        song.setAudioUrl(songDTO.getAudioUrl());
        song.setThumbnail(songDTO.getThumbnail());
        song.setLyrics(songDTO.getLyrics());
        song.setReleaseDate(songDTO.getReleaseDate());
        song.setViewCount(songDTO.getViewCount() != null ? songDTO.getViewCount() : 0);

        Artist artist = artistRepository.findById(songDTO.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        song.setArtist(artist);

        if (songDTO.getAlbumId() != null) {
            Album album = albumRepository.findById(songDTO.getAlbumId())
                    .orElseThrow(() -> new RuntimeException("Album not found"));
            song.setAlbum(album);
        }

        // Link genres
        if (songDTO.getGenreIds() != null && !songDTO.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(songDTO.getGenreIds());
            if (genres.size() != songDTO.getGenreIds().size()) {
                throw new RuntimeException("One or more genres not found");
            }
            song.setGenres(genres);
        }

        Song savedSong = songRepository.save(song);

        // Automatically add song to playlists with matching genres
        if (!song.getGenres().isEmpty()) {
            List<Playlist> matchingPlaylists = playlistRepository.findByGenresIn(song.getGenres());
            for (Playlist playlist : matchingPlaylists) {
                SongPlaylist songPlaylist = new SongPlaylist();
                songPlaylist.setSong(savedSong);
                songPlaylist.setPlaylist(playlist);
                songPlaylist.setAddedAt(LocalDateTime.now());
                songPlaylistRepository.save(songPlaylist);
            }
        }

        return mapToDTO(savedSong);
    }

    // Read by ID
    @Cacheable(value = "songs", key = "#id")
    public SongDTO getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        return mapToDTO(song);
    }

    // Read all with pagination
    @Cacheable(value = "songs", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<SongDTO> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    @CacheEvict(value = {"songs", "songsByGenre", "songsByArtist", "songPlaylists"}, allEntries = true)
    public SongDTO updateSong(Long id, SongDTO songDTO) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        song.setTitle(songDTO.getTitle());
        song.setDuration(songDTO.getDuration());
        song.setAudioUrl(songDTO.getAudioUrl());
        song.setThumbnail(songDTO.getThumbnail());
        song.setLyrics(songDTO.getLyrics());
        song.setReleaseDate(songDTO.getReleaseDate());
        song.setViewCount(songDTO.getViewCount());

        Artist artist = artistRepository.findById(songDTO.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        song.setArtist(artist);

        if (songDTO.getAlbumId() != null) {
            Album album = albumRepository.findById(songDTO.getAlbumId())
                    .orElseThrow(() -> new RuntimeException("Album not found"));
            song.setAlbum(album);
        } else {
            song.setAlbum(null);
        }

        // Update genres
        if (songDTO.getGenreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(songDTO.getGenreIds());
            if (genres.size() != songDTO.getGenreIds().size()) {
                throw new RuntimeException("One or more genres not found");
            }
            song.setGenres(genres);
        } else {
            song.setGenres(null);
        }

        // Remove song from old playlists
        List<SongPlaylist> existingSongPlaylists = songPlaylistRepository.findBySongId(song.getId());
        songPlaylistRepository.deleteAll(existingSongPlaylists);

        // Automatically add song to playlists with matching genres
        if (song.getGenres() != null && !song.getGenres().isEmpty()) {
            List<Playlist> matchingPlaylists = playlistRepository.findByGenresIn(song.getGenres());
            for (Playlist playlist : matchingPlaylists) {
                SongPlaylist songPlaylist = new SongPlaylist();
                songPlaylist.setSong(song);
                songPlaylist.setPlaylist(playlist);
                songPlaylist.setAddedAt(LocalDateTime.now());
                songPlaylistRepository.save(songPlaylist);
            }
        }

        Song updatedSong = songRepository.save(song);
        return mapToDTO(updatedSong);
    }

    // Delete
    @CacheEvict(value = {"songs", "songsByGenre", "songsByArtist", "songPlaylists"}, allEntries = true)
    public void deleteSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        songRepository.delete(song);
    }

    // Search songs
    @Cacheable(value = "songSearch", key = "#query + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<SongDTO> searchSongs(String query, Pageable pageable) {
        return songRepository.findByTitleContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    // Get songs by genre
    @Cacheable(value = "songsByGenre", key = "#genreId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<SongDTO> getSongsByGenre(Long genreId, Pageable pageable) {
        return songRepository.findByGenresId(genreId, pageable).map(this::mapToDTO);
    }

    // Get songs by artist
    @Cacheable(value = "songsByArtist", key = "#artistId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<SongDTO> getSongsByArtist(Long artistId, Pageable pageable) {
        return songRepository.findByArtistId(artistId, pageable).map(this::mapToDTO);
    }

    // Share song
    public String shareSong(Long id) {
        return "https://musicapp.com/song/" + id;
    }

    // Get song thumbnail
    @Cacheable(value = "songThumbnails", key = "#id")
    public String getSongThumbnail(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        return song.getThumbnail();
    }

    // Map entity to DTO
    private SongDTO mapToDTO(Song song) {
        SongDTO dto = new SongDTO();
        dto.setId(song.getId());
        dto.setTitle(song.getTitle());
        dto.setDuration(song.getDuration());
        dto.setAudioUrl(song.getAudioUrl());
        dto.setThumbnail(song.getThumbnail());
        dto.setLyrics(song.getLyrics());
        dto.setReleaseDate(song.getReleaseDate());
        dto.setViewCount(song.getViewCount());
        dto.setArtistId(song.getArtist().getId());
        dto.setAlbumId(song.getAlbum() != null ? song.getAlbum().getId() : null);
        dto.setGenreIds(song.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        return dto;
    }
}