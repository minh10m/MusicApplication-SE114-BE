package com.music.application.be.modules.playlist;

import com.music.application.be.modules.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT p FROM Playlist p JOIN p.genres g WHERE g IN :genres")
    List<Playlist> findByGenresIn(List<Genre> genres);
}
