package com.music.application.be.modules.album;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    @Transactional(readOnly = true)
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    @Transactional
    public Optional<Album> createAlbum(Album album, Long artistId) {
        return artistRepository.findById(artistId)
                .map(artist -> {
                    album.setArtist(artist);
                    return albumRepository.save(album);
                });
    }

    @Transactional
    public Optional<Album> updateAlbum(Long id, Album updatedAlbum) {
        return albumRepository.findById(id)
                .map(existingAlbum -> {
                    existingAlbum.setName(updatedAlbum.getName());
                    existingAlbum.setReleaseDate(updatedAlbum.getReleaseDate());
                    existingAlbum.setCoverImage(updatedAlbum.getCoverImage());
                    existingAlbum.setDescription(updatedAlbum.getDescription());
                    return albumRepository.save(existingAlbum);
                });
    }

    @Transactional
    public boolean deleteAlbum(Long id) {
        return albumRepository.findById(id)
                .map(album -> {
                    albumRepository.delete(album);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<Song> getSongsByAlbum(Long albumId) {
        return songRepository.findByAlbumId(albumId);
    }
}
