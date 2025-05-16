package com.music.application.be.modules.queue;

import com.music.application.be.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
    List<Queue> findByUserOrderByPositionAsc(User user);
    void deleteAllByUser(User user);
}

