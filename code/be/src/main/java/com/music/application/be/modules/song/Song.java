package com.music.application.be.modules.song;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.genre.Genre;
import com.music.application.be.modules.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "songs")
@Getter
@Setter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private int duration; // in seconds

    @Column(name = "audio_url")
    private String audioUrl;

    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "view_count", columnDefinition = "integer default 0")
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(mappedBy = "favoriteSongs")
    @JsonIgnore
    private Set<User> favoritedByUsers;
}
