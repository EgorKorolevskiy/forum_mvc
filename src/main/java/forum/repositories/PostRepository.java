package forum.repositories;

import forum.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query(value = "SELECT pe.creation_date, pe.post_name, pe.post_content  \n" +
            "FROM post_entity pe \n" +
            "JOIN user_entity ue ON pe.user_id = ue.id\n" +
            "WHERE ue.login = ?", nativeQuery = true)
    PostEntity findPostByUserLogin(String name);
}
