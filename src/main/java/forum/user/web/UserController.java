package forum.user.web;

import forum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Как нам передавать запросы - JSONом или через формочку?
 *
 * Для нашего юзера будем использовать такой адресный маппинг:
 * POST -- /users/add - создание нового юзера
 * href="user.html" - html форма для создания нового юзера
 * href="user_edit.html" - html форма редактирования юзера (для админа)
 * GET -- /users/{id} - найти юзера (для админа или в функционале поиска по юзерам форума)
 * PUT /users/{id} - обновляем запись о юзере (админ и модер)
 * DELETE - /users/{id} - удаление юзера (админ)
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * мы работаем через @RequestBody (JSON) или через String (@RequestParam) формочку?
     * @return
     */
    @PostMapping("/add")
    public String register(@RequestParam String login,
                           @RequestParam String password) {
        return userService.registrationUser(login, password);
    }
}
