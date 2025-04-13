package com.music.application.be.modules.song_playlist;

import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.song.Song;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "song_playlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SongPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;

    private Integer position; // Position of the song in the playlist

    // Constructors, getters, and setters are handled by Lombok
}