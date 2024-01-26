package forum.service;

import forum.exception.CustomException;
import forum.model.UserEntity;
import forum.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public UserEntity findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new CustomException("User not found"));
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
    public ResponseEntity<String> registrationUser(String login, String password) {
        var existLogin = findByLogin(login);
        if (existLogin != null) {
           throw new CustomException("Account already exists");
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
        return ResponseEntity.ok("Account created");
    }

    /**
     * Добавляем роль к юзеру по Id роли и Id юзера
     *
     * @param roleId
     * @param userId
     */
    public ResponseEntity<String> addRoleToUserById(long roleId, Long userId) {
        var user = findById(userId);
        var role = roleService.findById(roleId);
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.addRole(role, user);
        saveUser(user);
       return ResponseEntity.ok("Role added to user");
    }

    /**
     * Добавляем роль к юзеру по логину юзера и id роли из БД
     *
     * @param userLogin
     * @param roleId
     * @return
     */
    public ResponseEntity<String> addRoleToUserByLogin(String userLogin, Long roleId) {
        var user = findByLogin(userLogin);
        var role = roleService.findById(roleId);
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.addRole(role, user);
        saveUser(user);
        return ResponseEntity.ok(String.format("Role %s was added to user %s", role.getName(), user.getLogin()));
    }

    /**
     * Удаляем роль у юзера по логину юзера и id роли из БД
     *
     * @param userLogin
     * @param roleId
     * @return
     */
    public ResponseEntity<String> deleteRoleToUserByLogin(String userLogin, Long roleId) {
        var user = findByLogin(userLogin);
        var role = roleService.findById(roleId);
        if (user.getRoles() == null) {
            throw new CustomException("User don't have such role");
        }
        user.removeRole(role);
        saveUser(user);
        return ResponseEntity.ok(String.format("Role %s was removed from user %s", role.getName(), user.getLogin()));
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
        return findByLogin(username);
    }
}