package com.music.application.be.modules.queue;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QueueService {

    private final QueueRepository queueRepository;

    public Queue addToQueue(User user, Song song, Integer position) {
        Queue queueItem = Queue.builder()
                .user(user)
                .song(song)
                .position(position)
                .build();
        return queueRepository.save(queueItem);
    }

    @Cacheable(value = "userQueue", key = "#user.id")
    public List<Queue> getUserQueue(User user) {
        return queueRepository.findByUserOrderByPositionAsc(user);
    }

    public Optional<Queue> getQueueItem(Long id) {
        return queueRepository.findById(id);
    }

    @CacheEvict(value = "userQueue", key = "#user.id")
    public void removeFromQueue(Long id) {
        queueRepository.findById(id).ifPresent(queueItem ->
                queueRepository.deleteById(id));
    }

    @CachePut(value = "userQueue", key = "#user.id")
    public void updatePosition(Long id, Integer newPosition) {
        queueRepository.findById(id).ifPresent(queueItem -> {
            queueItem.setPosition(newPosition);
            queueRepository.save(queueItem);
        });
    }

    @CacheEvict(value = "userQueue", key = "#user.id")
    public void clearUserQueue(User user) {
        queueRepository.deleteAllByUser(user);
    }
}