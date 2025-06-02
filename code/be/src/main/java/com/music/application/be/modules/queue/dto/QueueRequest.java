package com.music.application.be.modules.queue.dto;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueRequest {
    private User user;
    private Song song;
    private Integer position;
}

