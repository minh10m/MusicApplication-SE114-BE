package com.music.application.be.modules.artist;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.album.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @CachePut(value = "artists", key = "#result.id")
    public ArtistDTO createArtist(ArtistDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        artist.setAvatar(artistDTO.getAvatar());
        artist.setDescription(artistDTO.getDescription());
        artist.setFollowerCount(artistDTO.getFollowerCount());

        // Liên kết các album nếu có
        if (artistDTO.getAlbumIds() != null && !artistDTO.getAlbumIds().isEmpty()) {
            List<Album> albums = albumRepository.findAllById(artistDTO.getAlbumIds());
            if (albums.size() != artistDTO.getAlbumIds().size()) {
                throw new RuntimeException("One or more albums not found");
            }
            artist.setAlbums(albums);
        }

        Artist savedArtist = artistRepository.save(artist);
        return mapToDTO(savedArtist);
    }

    // Read by ID
    @Cacheable(value = "artists", key = "#id")
    public ArtistDTO getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        return mapToDTO(artist);
    }

    // Read all with pagination
    @Cacheable(value = "artistPages", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ArtistDTO> getAllArtists(Pageable pageable) {
        return artistRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    @CachePut(value = "artists", key = "#id")
    public ArtistDTO updateArtist(Long id, ArtistDTO artistDTO) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        artist.setName(artistDTO.getName());
        artist.setAvatar(artistDTO.getAvatar());
        artist.setDescription(artistDTO.getDescription());
        artist.setFollowerCount(artistDTO.getFollowerCount());

        // Cập nhật danh sách album
        if (artistDTO.getAlbumIds() != null && !artistDTO.getAlbumIds().isEmpty()) {
            List<Album> albums = albumRepository.findAllById(artistDTO.getAlbumIds());
            if (albums.size() != artistDTO.getAlbumIds().size()) {
                throw new RuntimeException("One or more albums not found");
            }
            artist.setAlbums(albums);
        }

        Artist updatedArtist = artistRepository.save(artist);
        return mapToDTO(updatedArtist);
    }

    // Delete
    @CacheEvict(value = "artists", key = "#id")
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        artistRepository.delete(artist);
    }

    // Search artists
    @Cacheable(value = "artistPages", key = "#query + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
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
        dto.setAlbumIds(artist.getAlbums() != null ?
                artist.getAlbums().stream().map(Album::getId).collect(Collectors.toList()) : null);
        return dto;
    }
}