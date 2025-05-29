package com.music.application.be.modules.comment;

import com.music.application.be.modules.song.Song;
import com.music.application.be.modules.song.SongRepository;
import com.music.application.be.modules.user.User;
import com.music.application.be.modules.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentDTO createComment(Long songId, Long userId, String content, Long parentId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("Song not found with id: " + songId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Comment comment = new Comment();
        comment.setSong(song);
        comment.setUser(user);
        comment.setContent(content);
        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("Parent comment not found with id: " + parentId));
            comment.setParent(parent);
        }
        comment = commentRepository.save(comment);

        return mapToDTO(comment);
    }

    public CommentDTO likeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (!commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            CommentLike like = new CommentLike();
            like.setComment(comment);
            like.setUser(user);
            commentLikeRepository.save(like);
            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
        }

        return mapToDTO(comment);
    }

    public CommentDTO unlikeComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        if (commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
            comment.setLikes(comment.getLikes() - 1);
            if (comment.getLikes() < 0) comment.setLikes(0L); // Đảm bảo không âm
            commentRepository.save(comment);
        }

        return mapToDTO(comment);
    }

    public List<CommentDTO> getCommentsBySongId(Long songId) {
        List<Comment> comments = commentRepository.findBySongIdAndParentIsNull(songId);
        return comments.stream()
                .map(this::mapToDTOWithReplies)
                .collect(Collectors.toList());
    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setSongId(comment.getSong().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setContent(comment.getContent());
        dto.setParentId(comment.getParent() != null ? comment.getParent().getId() : null);
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setLikes(comment.getLikes());
        return dto;
    }

    private CommentDTO mapToDTOWithReplies(Comment comment) {
        CommentDTO dto = mapToDTO(comment);
        List<Comment> replies = commentRepository.findByParentId(comment.getId());
        dto.setReplies(replies.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}