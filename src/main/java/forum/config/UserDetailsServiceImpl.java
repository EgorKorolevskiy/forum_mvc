package forum.config;

import forum.model.RoleEntity;
import forum.model.UserEntity;
import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    // Спрингу достаточно знать о пользователе только его имя, пароль и права доступа. Всем этим заведует
    // интерфейс UserDetails. При попытке входа в аккаунт запускается метод loadUserByUsername
    // Используем @Transactional для lazy коллекции
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userService.findByLogin(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        UserEntity user = userOptional.get();
        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(getUsersRoles(user.getRoles()))
                .build();
    }

    // Из коллекции ролей возвращаем массив с правами доступа юзера
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
