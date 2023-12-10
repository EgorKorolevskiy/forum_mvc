package forum.models.DAO;

import forum.models.Comment;
import forum.models.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment findCommentById(long id) {
       return commentRepository.findById(id);
    }

    public void updateComment(Comment comment) {
        commentRepository.saveAndFlush(comment);
    }

    public void deleteCommentById(long id) {
        commentRepository.deleteById(id);
    }
}
