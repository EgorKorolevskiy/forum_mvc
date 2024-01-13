package forum.repositories;

import forum.model.BadWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadWordsRepository extends JpaRepository<BadWordsEntity, Long> {
     List<BadWordsEntity> findByWord(String word);
}
