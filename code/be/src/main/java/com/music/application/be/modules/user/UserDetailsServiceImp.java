package com.music.application.be.modules.user;

import com.music.application.be.modules.artist.Artist;
import com.music.application.be.modules.artist.ArtistRepository;
import com.music.application.be.modules.playlist.Playlist;
import com.music.application.be.modules.playlist.PlaylistRepository;
import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.dto.UserDTO;
import com.music.application.be.modules.user.dto.UserFavoritePlaylistsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

}