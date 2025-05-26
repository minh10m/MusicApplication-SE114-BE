package com.music.application.be.modules.album;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public AlbumDTO createAlbum(AlbumDTO albumDTO, MultipartFile coverImageFile) throws IOException {
        if (coverImageFile == null || coverImageFile.isEmpty()) {
            throw new IllegalArgumentException("Cover image is required");
        }
        if (albumDTO.getName() == null || albumDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Album name is required");
        }

        Artist artist = artistRepository.findById(albumDTO.getArtistId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Artist not found with id: " + albumDTO.getArtistId()));

        Album album = new Album();
        album.setName(albumDTO.getName());
        album.setReleaseDate(albumDTO.getReleaseDate());
        album.setDescription(albumDTO.getDescription());
        album.setArtist(artist);

        String coverImageUrl = cloudinaryService.uploadFile(coverImageFile, "image");
        album.setCoverImage(coverImageUrl);

        Album savedAlbum = albumRepository.save(album);
        return mapToDTO(savedAlbum);
    }

    public AlbumDTO updateAlbum(Long id, AlbumDTO albumDTO, MultipartFile coverImageFile) throws IOException {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Album not found with id: " + id));

        Artist artist = artistRepository.findById(albumDTO.getArtistId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Artist not found with id: " + albumDTO.getArtistId()));

        album.setName(albumDTO.getName());
        album.setReleaseDate(albumDTO.getReleaseDate());
        album.setDescription(albumDTO.getDescription());
        album.setArtist(artist);

        if (coverImageFile != null && !coverImageFile.isEmpty()) {
            String coverImageUrl = cloudinaryService.uploadFile(coverImageFile, "image");
            album.setCoverImage(coverImageUrl);
        }

        Album updatedAlbum = albumRepository.save(album);
        return mapToDTO(updatedAlbum);
    }

    public AlbumDTO getAlbumById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        return mapToDTO(album);
    }

    public Page<AlbumDTO> getAllAlbums(Pageable pageable) {
        return albumRepository.findAll(pageable).map(this::mapToDTO);
    }

    public void deleteAlbum(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));
        albumRepository.delete(album);
    }

    public Page<AlbumDTO> searchAlbums(String query, Pageable pageable) {
        return albumRepository.findByNameContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    public Page<AlbumDTO> getAlbumsByArtist(Long artistId, Pageable pageable) {
        return albumRepository.findByArtistId(artistId, pageable).map(this::mapToDTO);
    }

    public String shareAlbum(Long id) {
        return "https://musicapp.com/album/" + id;
    }

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