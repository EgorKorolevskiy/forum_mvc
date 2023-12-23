package forum.service;

import forum.model.CommentEntity;
import forum.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComment(CommentEntity commentEntity) {
        commentRepository.save(commentEntity);
    }

    public Optional<CommentEntity> findCommentById(long id) {
       return commentRepository.findById(id);
    }

    public void updateComment(CommentEntity commentEntity) {
        commentRepository.saveAndFlush(commentEntity);
    }

    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }
}
