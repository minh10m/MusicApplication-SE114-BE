package com.music.application.be.modules.user.dto;

import com.music.application.be.modules.playlist.Playlist;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserFavoritePlaylistsDTO {
    private Long userId;
    private List<Playlist> playlists;
    private int totalPlaylists;
}