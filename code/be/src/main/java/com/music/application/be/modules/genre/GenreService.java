package com.music.application.be.modules.genre;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final SongRepository songRepository;

    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Transactional
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    public Optional<Genre> updateGenre(Long id, Genre updatedGenre) {
        return genreRepository.findById(id)
                .map(existingGenre -> {
                    existingGenre.setName(updatedGenre.getName());
                    existingGenre.setDescription(updatedGenre.getDescription());
                    return genreRepository.save(existingGenre);
                });
    }

    @Transactional
    public boolean deleteGenre(Long id) {
        return genreRepository.findById(id)
                .map(genre -> {
                    genreRepository.delete(genre);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<Song> getSongsByGenre(Long genreId) {
        return songRepository.findAll().stream()
                .filter(song -> song.getGenre() != null && song.getGenre().getId().equals(genreId))
                .toList();
    }
}
