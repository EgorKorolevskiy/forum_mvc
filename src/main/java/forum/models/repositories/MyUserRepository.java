package forum.models.repositories;

import forum.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    @Override
    <S extends MyUser> S save(S entity);
    MyUser findById(long id);
    Optional<MyUser> findByLogin(String login);
    @Override
    <S extends MyUser> S saveAndFlush(S entity);
    void deleteById(long id);
}
