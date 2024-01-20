package forum.repositories;

import forum.model.BadWordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadWordsRepository extends JpaRepository<BadWordsEntity, Long> {
    Optional<String> findByWord(String word);


}
