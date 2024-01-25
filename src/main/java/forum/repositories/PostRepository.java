package forum.repositories;

import forum.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
//    @Query(value = "SELECT pe.creation_date, pe.post_name, pe.post_content  \n" +
//            "FROM post_entity pe \n" +
//            "JOIN user_entity ue ON pe.user_id = ue.id\n" +
//            "WHERE ue.login = ?", nativeQuery = true)

    //JPQL
    @Query(value = """
            SELECT pe FROM PostEntity pe WHERE pe.user.id = :userId
            """)
    List<PostEntity> findPostByUserLogin(@Param("userId") Long userId);

    @Override
    List<PostEntity> findAll();

    @Query(value = """
            select * from post_entity pe where creation_date = ?
            """, nativeQuery = true)
    List<PostEntity> findByCreationDate(LocalDate date);

    @Query(value = """
            select ue.login from user_entity ue
            join post_entity pe on ue.id = pe.user_id
            where pe.id = ?
            """, nativeQuery = true)
    String findUserLoginByPostId(Long id);
}
