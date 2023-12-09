package forum.models.repositories;

import forum.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    <S extends Comment> S save(S entity);
    Comment findById(long id);
    @Override
    <S extends Comment> S saveAndFlush(S entity);
    void deleteById(long id);
}
