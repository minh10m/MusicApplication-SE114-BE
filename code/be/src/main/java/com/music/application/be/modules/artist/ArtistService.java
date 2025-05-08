package com.music.application.be.modules.artist;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.album.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    // Create
    public ArtistDTO createArtist(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        artist.setAvatar(artistDTO.getAvatar());
        artist.setDescription(artistDTO.getDescription());
        artist.setFollowerCount(artistDTO.getFollowerCount());

        Artist savedArtist = artistRepository.save(artist);
        return mapToDTO(savedArtist);
    }

    // Read by ID
    public ArtistDTO getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        return mapToDTO(artist);
    }

    // Read all with pagination
    public Page<ArtistDTO> getAllArtists(Pageable pageable) {
        return artistRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    public ArtistDTO updateArtist(Long id, ArtistDTO artistDTO) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artist.setName(artistDTO.getName());
        artist.setAvatar(artistDTO.getAvatar());
        artist.setDescription(artistDTO.getDescription());
        artist.setFollowerCount(artistDTO.getFollowerCount());

        Artist updatedArtist = artistRepository.save(artist);
        return mapToDTO(updatedArtist);
    }

    // Delete
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        artistRepository.delete(artist);
    }

    // Search artists
    public Page<ArtistDTO> searchArtists(String query, Pageable pageable) {
        return artistRepository.findByNameContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    // Share artist
    public String shareArtist(Long id) {
        return "https://musicapp.com/artist/" + id;
    }

    // Map entity to DTO
    private ArtistDTO mapToDTO(Artist artist) {
        ArtistDTO dto = new ArtistDTO();
        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setAvatar(artist.getAvatar());
        dto.setDescription(artist.getDescription());
        dto.setFollowerCount(artist.getFollowerCount());
        return dto;
    }
}