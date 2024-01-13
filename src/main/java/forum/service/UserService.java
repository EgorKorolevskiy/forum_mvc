package forum.service;

import forum.model.UserEntity;
import forum.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    public Optional<UserEntity> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.saveAndFlush(userEntity);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Метод регистрации юзера
     *
     * @param login
     * @param password
     * @return
     */
    public String registrationUser(String login, String password) {
        var existLogin = findByLogin(login);
        if (existLogin.isPresent()) {
            return "Account already exists";
        }
        // шифруем пароль в хеш через BCrypt
        var passwordEncoder = new BCryptPasswordEncoder();
        final var hashedPass = passwordEncoder.encode(password);
        // создаем юзера
        var user = UserEntity.builder()
                .login(login)
                .password(hashedPass)
                .build();
        // сохраняем юзера
        saveUser(user);
        // добавляем юзера к роли (по дефолту первая регистрация добавляется роль USER)
        addRoleToUserById(roleService.findIdByName("USER"), user.getId());
        return "Account created";
    }

    /**
     * Добавляем роль к юзеру по Id роли и Id юзера
     *
     * @param roleId
     * @param userId
     */
    public void addRoleToUserById(long roleId, Long userId) {
        var user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        var role = roleService.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.addRole(role, user);
        saveUser(user);
    }

    /**
     * Добавляем роль к юзеру по логину юзера и id роли из БД
     *
     * @param userLogin
     * @param roleId
     * @return
     */
    public String addRoleToUserByLogin(String userLogin, Long roleId) {
        var user = findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
        var role = roleService.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.addRole(role, user);
        saveUser(user);
        return String.format("Role %s was added to user %s", role.getName(), user.getLogin());
    }

    /**
     * Удаляем роль у юзера по логину юзера и id роли из БД
     *
     * @param userLogin
     * @param roleId
     * @return
     */
    public String deleteRoleToUserByLogin(String userLogin, Long roleId) {
        var user = findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
        var role = roleService.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        if (user.getRoles() == null) {
            return "User don't have such role";
        }
        user.removeRole(role);
        saveUser(user);
        return String.format("Role %s was removed from user %s", role.getName(), user.getLogin());
    }

    /**
     * Получение текущего авторизованного Optional юзера по логину из контекста спринга
     *
     * @return
     */
    public UserEntity getCurrentUserByPrincipal() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();
        final var username = user.getUsername();
        return findByLogin(username).get();
    }

    /**
     * Возвращаем все роли у авторизованного юзера
     *
     * @return
     */

    public Collection<? extends GrantedAuthority> getUserAuthorities() {
        // Получение аутентификации пользователя
        // Получение всех ролей пользователя
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}