package forum.models.DAO;

import forum.models.Role;
import forum.models.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role findRoleById(long id) {
        return roleRepository.findById(id);
    }

    public void updateRole(Role role) {
        roleRepository.saveAndFlush(role);
    }

    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }
}
