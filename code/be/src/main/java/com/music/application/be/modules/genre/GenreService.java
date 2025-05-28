package com.music.application.be.modules.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    // Create
    @CacheEvict(value = {"genres", "genreSearch", "songsByGenre", "playlists"}, allEntries = true)
    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        Genre savedGenre = genreRepository.save(genre);
        return mapToDTO(savedGenre);
    }

    // Read by ID
    @Cacheable(value = "genres", key = "#id")
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        return mapToDTO(genre);
    }

    // Read all with pagination
    @Cacheable(value = "genres", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<GenreDTO> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable).map(this::mapToDTO);
    }

    // Update
    @CacheEvict(value = {"genres", "genreSearch", "songsByGenre", "playlists"}, allEntries = true)
    public GenreDTO updateGenre(Long id, GenreDTO genreDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        genre.setName(genreDTO.getName());
        genre.setDescription(genreDTO.getDescription());

        Genre updatedGenre = genreRepository.save(genre);
        return mapToDTO(updatedGenre);
    }

    // Delete
    @CacheEvict(value = {"genres", "genreSearch", "songsByGenre", "playlists"}, key = "#id")
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        genreRepository.delete(genre);
    }

    // Search genres
    @Cacheable(value = "genreSearch", key = "#query + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<GenreDTO> searchGenres(String query, Pageable pageable) {
        return genreRepository.findByNameContainingIgnoreCase(query, pageable).map(this::mapToDTO);
    }

    // Map entity to DTO
    private GenreDTO mapToDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());
        return dto;
    }
}