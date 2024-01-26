package forum.service;

import forum.exception.CustomException;
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

    public RoleEntity findById(long id) {
        return roleRepository.findById(id).orElseThrow(() -> new CustomException("Role not found"));
    }

    public void updateRole(RoleEntity roleEntity) {
        roleRepository.saveAndFlush(roleEntity);
    }

    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }
}
