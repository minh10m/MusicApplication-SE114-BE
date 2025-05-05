package com.music.application.be.modules.album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Album> findByArtistId(Long artistId, Pageable pageable);
}