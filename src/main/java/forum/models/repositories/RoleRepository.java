package forum.models.repositories;

import forum.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    <S extends Role> S save(S entity);
    Role findById(long id);
    @Override
    <S extends Role> S saveAndFlush(S entity);
    void deleteById(long id);
}
