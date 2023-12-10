package forum.user.model;

import forum.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findById(long id);
    Optional<UserEntity> findByLogin(String login);
    void deleteById(long id);
}
