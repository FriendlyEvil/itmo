package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.repository.CommentRepository;
import ru.itmo.wm4.repository.NoticeRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findByOrderByCreationTimeDesc() {
        return commentRepository.findByOrderByCreationTimeDesc();
    }

    public void save(Comment comment, User user, Notice notice) {
        user.addComment(comment);
        notice.addComment(comment);
        commentRepository.save(comment);
    }

    public Comment findById(Long userId) {
        return userId == null ? null : commentRepository.findById(userId).orElse(null);
    }

}
