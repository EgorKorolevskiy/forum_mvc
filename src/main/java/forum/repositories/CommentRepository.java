package forum.repositories;

import forum.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = """
            select ue.login from user_entity ue
            join comment_entity ce on ue.id = ce.user_id
            where ce.id = ?
            """, nativeQuery = true)
    String findUserLoginByCommentId(Long commentId);
}
