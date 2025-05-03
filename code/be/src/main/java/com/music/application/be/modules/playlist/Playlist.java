package com.music.application.be.modules.playlist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.music.application.be.modules.song_playlist.SongPlaylist;
import com.music.application.be.modules.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "playlists")
@Getter
@Setter
@NoArgsConstructor

public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;  // Assuming owner_id references a user ID

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String type;  // Could be enum if you have specific types

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<SongPlaylist> songs = new ArrayList<>();

    @ManyToMany(mappedBy = "favoritePlaylists")
    @JsonIgnore
    private Set<User> favoritedByUsers;

}
