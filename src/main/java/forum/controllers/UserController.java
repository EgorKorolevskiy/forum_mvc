package forum.controllers;

import forum.models.DAO.MyUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Как нам передавать запросы - JSONом или через формочку?
 *
 * Для нашего юзера будем использовать такой адресный маппинг:
 * POST -- /users - создание нового юзера
 * GET -- /users/new - html форма для создания нового юзера
 * GET -- /users/id/edit - html форма редактирования юзера (для админа)
 * GET -- /users/id - найти юзера (для админа или в функционале поиска по юзерам форума)
 * PATCH(частичное обновление юзера... или PUT лучше?) -- /users/id - обновляем запись о юзере (админ и модер)
 * DELETE - /users/id - удаление юзера (админ)
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private MyUserDAO myUserDAO;

    /**
     * мы работаем через @RequestBody (JSON) или через String (@RequestParam) формочку?
     * @return
     */
    @PostMapping()
    public String register(@RequestParam String login,
                           @RequestParam String password) {
        return myUserDAO.registrationUser(login, password);
    }
}
