package forum.config;

import forum.model.RoleEntity;
import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Используем IOC для вендрения зависимости через @RequiredArgsConstructor с пометкой final у нужного поля
     */
    private final UserService userService;

    // Спрингу достаточно знать о пользователе только его имя, пароль и права доступа. Всем этим заведует
    // интерфейс UserDetails. При попытке входа в аккаунт запускается метод loadUserByUsername
    // Используем @Transactional для lazy коллекции

    /**
     * Возвращает пользователя при попытке входа в аккаунт
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(getUsersRoles(user.getRoles()))
                .build();
    }

    /**
     * Из коллекции ролей возвращаем массив с правами доступа юзера
     *
     * @param roles коллекция ролей пользователя
     * @return массив имен пользователя
     */
    private String[] getUsersRoles(Set<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getName)
                .toArray(String[]::new);
    }


    // возникает проблема, что спринг требует передать кроме логина и пароля еще и права доступа, но у нас
    // есть только роли. Поэтому из списка ролей делаем список прав доступа с точно такими же строками
    // через метод mapRolesToAuthorities
    // Используем @Transactional для lazy коллекции

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserEntity> userOptional = userService.findByLogin(username);
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException(String.format("User %s not found", username));
//        }
//        UserEntity userEntity = userOptional.get();
//        return new User(userEntity.getLogin(),
//                userEntity.getPassword(),
//                mapRolesToAuthorities(userEntity.getRoles()));
//    }
//
//    // Мапим коллекцию ролей в коллекцию авторизованных прав
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleEntity> roles) {
//        return roles.stream().map(t -> new SimpleGrantedAuthority(t.getName())).toList();
//    }
}
