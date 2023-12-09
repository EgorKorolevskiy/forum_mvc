package forum.models.DAO;

import forum.models.Comment;
import forum.models.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentDAO {
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
