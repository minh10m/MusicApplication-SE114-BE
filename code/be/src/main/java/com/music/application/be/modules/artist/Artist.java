package com.music.application.be.modules.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.music.application.be.modules.album.Album;
import com.music.application.be.modules.follow_artist.FollowArtist;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<Song> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "followedArtists")
    @JsonIgnore
    private Set<User> followers;
}