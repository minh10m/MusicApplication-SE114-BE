package com.music.application.be.modules.recently_played;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentlyPlayedRequest {
    private User user;
    private Song song;
}


