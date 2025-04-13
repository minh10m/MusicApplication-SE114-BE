package com.music.application.be.modules.artist;

import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.follow_artist.FollowArtist;
import com.music.application.be.modules.song.Song;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
@Getter
@Setter
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String avatar;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "follower_count", columnDefinition = "integer default 0")
    private int followerCount = 0;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowArtist> followers = new ArrayList<>();
}