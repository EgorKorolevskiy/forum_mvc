package forum.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeMenuController {

    // перенаправляет на страницу авторизации и регистрации
    @GetMapping("/")
    public ModelAndView homePage () {
        return new ModelAndView("redirect:/views/home/menu.html");
    }
}
