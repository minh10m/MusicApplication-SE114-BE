package com.music.application.be.modules.album;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.song.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    // Create
    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        Artist artist = artistRepository.findById(albumDTO.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        Album album = new Album();
        album.setName(albumDTO.getName());
        album.setReleaseDate(albumDTO.getReleaseDate());
        album.setCoverImage(albumDTO.getCoverImage());
        album.setDescription(albumDTO.getDescription());
        album.setArtist(artist);

        Album savedAlbum = albumRepository.save(album);
        return mapToDTO(savedAlbum);
    }

    // Read by ID
    public AlbumDTO getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        return mapToDTO(album);
    }

    // Read all with pagination
    public Page<AlbumDTO> getAllAlbums(Pageable pageable) {
        return albumRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    public AlbumDTO updateAlbum(Long id, AlbumDTO albumDTO) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        Artist artist = artistRepository.findById(albumDTO.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        album.setName(albumDTO.getName());
        album.setReleaseDate(albumDTO.getReleaseDate());
        album.setCoverImage(albumDTO.getCoverImage());
        album.setDescription(albumDTO.getDescription());
        album.setArtist(artist);

        Album updatedAlbum = albumRepository.save(album);
        return mapToDTO(updatedAlbum);
    }

    // Delete
    public void deleteAlbum(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        albumRepository.delete(album);
    }

    // Search albums
    public Page<AlbumDTO> searchAlbums(String query, Pageable pageable) {
        return albumRepository.findByNameContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    // Get albums by artist
    public Page<AlbumDTO> getAlbumsByArtist(Long artistId, Pageable pageable) {
        return albumRepository.findByArtistId(artistId, pageable).map(this::mapToDTO);
    }

    // Share album
    public String shareAlbum(Long id) {
        return "https://musicapp.com/album/" + id;
    }

    // Map entity to DTO
    private AlbumDTO mapToDTO(Album album) {
        AlbumDTO dto = new AlbumDTO();
        dto.setId(album.getId());
        dto.setName(album.getName());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setCoverImage(album.getCoverImage());
        dto.setDescription(album.getDescription());
        dto.setArtistId(album.getArtist().getId());
        return dto;
    }
}