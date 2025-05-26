package com.music.application.be.modules.song;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.album.AlbumRepository;
import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.cloudinary.CloudinaryService;
import com.music.application.be.modules.genre.Genre;
import com.music.application.be.modules.genre.GenreRepository;
import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.song_playlist.SongPlaylist;
import com.music.application.be.modules.song_playlist.SongPlaylistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private CloudinaryService cloudinaryService;

    public SongDTO createSong(SongDTO songDTO, MultipartFile audioFile, MultipartFile thumbnailFile) throws IOException {
        if (audioFile == null || audioFile.isEmpty()) {
            throw new IllegalArgumentException("Audio file is required");
        }
        if (thumbnailFile == null || thumbnailFile.isEmpty()) {
            throw new IllegalArgumentException("Thumbnail file is required");
        }
        if (songDTO.getTitle() == null || songDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Song title is required");
        }

        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        song.setDuration(songDTO.getDuration());
        song.setLyrics(songDTO.getLyrics());
        song.setReleaseDate(songDTO.getReleaseDate());
        song.setViewCount(songDTO.getViewCount() != null ? songDTO.getViewCount() : 0);

        String audioUrl = cloudinaryService.uploadFile(audioFile, "video");
        song.setAudioUrl(audioUrl);

        String thumbnailUrl = cloudinaryService.uploadFile(thumbnailFile, "image");
        song.setThumbnail(thumbnailUrl);

        Artist artist = artistRepository.findById(songDTO.getArtistId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + songDTO.getArtistId()));
        song.setArtist(artist);

        if (songDTO.getAlbumId() != null) {
            Album album = albumRepository.findById(songDTO.getAlbumId())
                    .orElseThrow(() -> new EntityNotFoundException("Album not found with id: " + songDTO.getAlbumId()));
            song.setAlbum(album);
        }

        if (songDTO.getGenreIds() != null && !songDTO.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(songDTO.getGenreIds());
            if (genres.size() != songDTO.getGenreIds().size()) {
                throw new IllegalArgumentException("One or more genres not found");
            }
            song.setGenres(genres);
        }

        Song savedSong = songRepository.save(song);

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

    public SongDTO updateSong(Long id, SongDTO songDTO, MultipartFile audioFile, MultipartFile thumbnailFile) throws IOException {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));

        song.setTitle(songDTO.getTitle());
        song.setDuration(songDTO.getDuration());
        song.setLyrics(songDTO.getLyrics());
        song.setReleaseDate(songDTO.getReleaseDate());
        song.setViewCount(songDTO.getViewCount());

        if (audioFile != null && !audioFile.isEmpty()) {
            String audioUrl = cloudinaryService.uploadFile(audioFile, "video");
            song.setAudioUrl(audioUrl);
        }

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadFile(thumbnailFile, "image");
            song.setThumbnail(thumbnailUrl);
        }

        Artist artist = artistRepository.findById(songDTO.getArtistId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found with id: " + songDTO.getArtistId()));
        song.setArtist(artist);

        if (songDTO.getAlbumId() != null) {
            Album album = albumRepository.findById(songDTO.getAlbumId())
                    .orElseThrow(() -> new EntityNotFoundException("Album not found with id: " + songDTO.getAlbumId()));
            song.setAlbum(album);
        } else {
            song.setAlbum(null);
        }

        if (songDTO.getGenreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(songDTO.getGenreIds());
            if (genres.size() != songDTO.getGenreIds().size()) {
                throw new IllegalArgumentException("One or more genres not found");
            }
            song.setGenres(genres);
        } else {
            song.setGenres(null);
        }

        List<SongPlaylist> existingSongPlaylists = songPlaylistRepository.findBySongId(song.getId());
        songPlaylistRepository.deleteAll(existingSongPlaylists);

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

    public SongDTO getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));
        return mapToDTO(song);
    }

    public Page<SongDTO> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable).map(this::mapToDTO);
    }

    public void deleteSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));
        songRepository.delete(song);
    }

    public Page<SongDTO> searchSongs(String query, Pageable pageable) {
        return songRepository.findByTitleContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    public Page<SongDTO> getSongsByGenre(Long genreId, Pageable pageable) {
        return songRepository.findByGenresId(genreId, pageable).map(this::mapToDTO);
    }

    public Page<SongDTO> getSongsByArtist(Long artistId, Pageable pageable) {
        return songRepository.findByArtistId(artistId, pageable).map(this::mapToDTO);
    }

    public String shareSong(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));
        return "https://musicapp.com/song/" + id;
    }

    public String getSongThumbnail(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + id));
        return song.getThumbnail();
    }

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