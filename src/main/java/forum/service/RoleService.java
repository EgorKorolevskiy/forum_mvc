package forum.service;

import forum.model.RoleEntity;
import forum.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void save(RoleEntity entity) {
        roleRepository.save(entity);
    }

    public long findIdByName(String name) {
        return roleRepository.findIdByName(name);
    }

    public Optional<RoleEntity> findById(long id) {
        return roleRepository.findById(id);
    }

    public void updateRole(RoleEntity roleEntity) {
        roleRepository.saveAndFlush(roleEntity);
    }

    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }
}
