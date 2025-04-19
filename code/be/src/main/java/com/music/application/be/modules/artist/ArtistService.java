package com.music.application.be.modules.artist;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.album.AlbumRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Transactional(readOnly = true)
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }

    @Transactional
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Transactional
    public Optional<Artist> updateArtist(Long id, Artist updatedArtist) {
        return artistRepository.findById(id)
                .map(existingArtist -> {
                    existingArtist.setName(updatedArtist.getName());
                    existingArtist.setAvatar(updatedArtist.getAvatar());
                    existingArtist.setDescription(updatedArtist.getDescription());
                    return artistRepository.save(existingArtist);
                });
    }

    @Transactional
    public boolean deleteArtist(Long id) {
        return artistRepository.findById(id)
                .map(artist -> {
                    artistRepository.delete(artist);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<Album> getAlbumsByArtist(Long artistId) {
        return albumRepository.findByArtistId(artistId);
    }

    @Transactional(readOnly = true)
    public List<Song> getSongsByArtist(Long artistId) {
        return songRepository.findByArtistId(artistId);
    }
}
