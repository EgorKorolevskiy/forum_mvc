package forum.controllers;

import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumMenuController {
    private final UserService userService;

    @GetMapping("/")
    public ModelAndView homePage () {
        return new ModelAndView("redirect:/views/forum/main.html");
    }
}
