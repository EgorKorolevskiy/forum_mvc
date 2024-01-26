package forum.controllers;

import forum.dto.UserDto;
import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * POST -- /user/add - создание нового юзера
 * PUT -- /user/add - создание нового юзера
 * PUT -- /user/admin/addRole/{userLogin}/{roleId} - Админка. Юзер с ролью админа добавляет роль другому юзеру
 * DEL -- /user/{role}/deleteRole/{userLogin}/{roleId} -Удаляет роль у юзера
 * <p>
 * (старые варианты)
 * href="user.html" - html форма для создания нового юзера
 * href="user_edit.html" - html форма редактирования юзера (для админа)
 * GET -- /users/{id} - найти юзера (для админа или в функционале поиска по юзерам форума)
 * PUT /users/{id} - обновляем запись о юзере (админ и модер)
 * DELETE - /users/{id} - удаление юзера (админ)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * Добавление юзера.
     *
     * @param userDTO С представления получаем ДТО (логин и пароль). А на беке уже создаем полную сущность
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto userDTO) {
        return userService.registrationUser(userDTO.getLogin(), userDTO.getPassword());
    }

    /**
     * Админка. Юзер с ролью админа добавляет роль другому юзеру
     *
     * @param userLogin Логин юзера которому добавляем роль
     * @param roleId    Id роли из БД
     * @return
     */

    @PutMapping("/admin/addRole/{userLogin}/{roleId}")
    public ResponseEntity<String> adminAddRoleToUser(@PathVariable String userLogin, @PathVariable Long roleId) {
        return userService.addRoleToUserByLogin(userLogin, roleId);
    }

    /**
     * Удаляет роль у юзера
     *
     * @param userLogin
     * @param roleId
     * @return
     */

    @DeleteMapping("{role}/deleteRole/{userLogin}/{roleId}")
    public ResponseEntity<String> adminDeleteRoleToUser(@PathVariable String userLogin, @PathVariable Long roleId) {
        return userService.deleteRoleToUserByLogin(userLogin, roleId);
    }


//    @GetMapping("/admin/test")
//    public String adminTest(Authentication authentication) {
//        var user = (User) authentication.getPrincipal();
//        final var username = user.getUsername();
//        String role = "";
//
//        for (GrantedAuthority authority : user.getAuthorities()) {
//            role = authority.getAuthority();
//            break;
//        }
//
//        userService.findByLogin(username);
//
//        return "Admin access! users/admin/test -- security: " + username + ", Role: " + role;
//    }
//
//
//    @GetMapping("/moder/test")
//    public String moderTest(Authentication authentication) {
//        User user = (User) authentication.getPrincipal();
//        String username = user.getUsername();
//        String role = "";
//
//        for (GrantedAuthority authority : user.getAuthorities()) {
//            role = authority.getAuthority();
//            break;
//        }
//
//        userService.findByLogin(username);
//
//        return "Moder access! Users/moder/test -- security: " + username + ", Role: " + role;
//    }
//
//
//    @GetMapping("/user/test")
//    public String userTest(Authentication authentication) {
//        User user = (User) authentication.getPrincipal();
//        String username = user.getUsername();
//        String role = "";
//
//        for (GrantedAuthority authority : user.getAuthorities()) {
//            role = authority.getAuthority();
//            break;
//        }
//
//        userService.findByLogin(username);
//
//        return "User access!  users/user/test -- security: " + username + ", Role: " + role;
//    }
//
//
//    @GetMapping("/user/test2")
//    public List<String> userTest() {
//        Optional<UserEntity> user = userService.findByLogin("user");
//        return user.get().getRoles().stream().map(t -> t.getName()).toList();
//    }

}
