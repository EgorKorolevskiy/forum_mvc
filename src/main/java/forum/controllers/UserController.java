package forum.controllers;

import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;



/**
 * Как нам передавать запросы - JSONом или через формочку?
 * <p>
 * Для нашего юзера будем использовать такой адресный маппинг:
 * POST -- /users/add - создание нового юзера
 * href="user.html" - html форма для создания нового юзера
 * href="user_edit.html" - html форма редактирования юзера (для админа)
 * GET -- /users/{id} - найти юзера (для админа или в функционале поиска по юзерам форума)
 * PUT /users/{id} - обновляем запись о юзере (админ и модер)
 * DELETE - /users/{id} - удаление юзера (админ)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final AuthorizedMenuController authorizedMenuController;
    private final UserService userService;

    @GetMapping("/admin/test")
    public String adminTest(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String role = "";

        for (GrantedAuthority authority : user.getAuthorities()) {
            role = authority.getAuthority();
            break;
        }

        userService.findByLogin(username);

        return "Admin access! users/admin/test -- security: " + username + ", Role: " + role;
    }


    @GetMapping("/moder/test")
    public String moderTest(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String role = "";

        for (GrantedAuthority authority : user.getAuthorities()) {
            role = authority.getAuthority();
            break;
        }

        userService.findByLogin(username);

        return "Moder access! Users/moder/test -- security: " + username + ", Role: " + role;
    }


    @GetMapping("/user/test")
    public String userTest(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String role = "";

        for (GrantedAuthority authority : user.getAuthorities()) {
            role = authority.getAuthority();
            break;
        }

        userService.findByLogin(username);

        return "User access!  users/user/test -- security: " + username + ", Role: " + role;
    }

}
