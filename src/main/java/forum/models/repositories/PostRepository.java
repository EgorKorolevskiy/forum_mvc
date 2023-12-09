package forum.models.repositories;

import forum.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    <S extends Post> S save(S entity);
    Post findById(long id);
    @Override
    <S extends Post> S saveAndFlush(S entity);
    void deleteById(long id);
}
