package com.music.application.be.modules.genre;

import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.song.Song;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "genres")
    private List<Song> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "genres")
    private List<Playlist> playlists = new ArrayList<>();
}