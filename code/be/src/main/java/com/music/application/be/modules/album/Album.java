package com.music.application.be.modules.album;


import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.song.Song;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albums")
@Getter
@Setter
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cover_image", columnDefinition = "TEXT", nullable = false)
    private String coverImage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
}
