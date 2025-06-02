package com.music.application.be.modules.recently_played;

import com.music.application.be.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentlyPlayedRepository extends JpaRepository<RecentlyPlayed, Long> {
    List<RecentlyPlayed> findByUserOrderByPlayedAtDesc(User user);
}
