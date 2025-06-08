package com.music.application.be.modules.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySongIdAndParentIsNull(Long songId);
    List<Comment> findByParentId(Long parentId);
}
