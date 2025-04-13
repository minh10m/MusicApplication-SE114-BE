package com.music.application.be.modules.follow_artist;

import com.music.application.be.modules.artist.Artist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "follow_artist")
@Getter
@Setter
public class FollowArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "followed_at", nullable = false)
    private LocalDateTime followedAt = LocalDateTime.now();
}