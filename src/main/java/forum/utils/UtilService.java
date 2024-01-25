package forum.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * Утилитарный класс.
 */

public class UtilService {

    /**
     * Утилитарный метод проверки на роль модера или админа у авторизованного юзера
     *
     * @param authorities
     * @return
     */
    public static boolean hasAdminOrModeratorRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .anyMatch(t -> t.getAuthority().equals("ROLE_ADMIN") || t.getAuthority().equals("ROLE_MODER"));
    }

    /**
     * Возвращаем все роли у авторизованного юзера
     *
     * @return
     */

    public static Collection<? extends GrantedAuthority> getUserAuthorities() {
        // Получение аутентификации пользователя
        // Получение всех ролей пользователя
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
