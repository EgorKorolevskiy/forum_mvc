package forum.service;

import forum.config.SecurityConfig;
import forum.model.UserEntity;
import forum.repositories.UserRepository;
import forum.model.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public Optional <UserEntity> findById(long id) {
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

    public String registrationUser(String login, String password) {
        Optional<UserEntity> existLogin = findByLogin(login);
        if (existLogin.isEmpty()) {
            // шифруем пароль в хеш через BCrypt
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPass = passwordEncoder.encode(password);
            // создаем юзера
            UserEntity user = UserEntity.builder()
                    .login(login)
                    .password(hashedPass)
                    .build();
            // сохраняем юзера
            saveUser(user);
            // добавляем юзера к роли (по дефолту первая регистрация добавляется роль USER)
            addUserToRole(roleService.findIdByName("USER"), user.getId());
            return "Account created";
        }
        return "Account already exists";
    }

    public void addUserToRole(long roleId, Long userId) {
        UserEntity user = findById(userId).get();
        RoleEntity role = roleService.findById(roleId).get();
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.addRole(role, user);
        saveUser(user);
    }

    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }


}

//    public String registrationUser(String login, String password) {
//        Optional<UserEntity> existLogin = findByLogin(login);
//        if (existLogin.isEmpty()) {
//            addUser(UserEntity.builder()
//                    .login(login)
//                    .password(password)
//                    .build());
//            return "Account created";
//        }
//        return "Account already exists";
//    }

//    public String registrationUser(String login, String password) {
//        Optional<UserEntity> existLogin = findByLogin(login);
//        if (existLogin.isEmpty()) {
//            var role = roleService.findRoleByName("user");
//            if (role != null) {
//                Set<RoleEntity> userRoles = new HashSet<>();
//                userRoles.add(role);
//                addUser(UserEntity.builder()
//                        .login(login)
//                        .password(password)
//                        .roles(userRoles)
//                        .build());
//                return "Account created";
//            }
//            return "Role not found";
//        }
//        return "Account already exists";
//    }
//}



