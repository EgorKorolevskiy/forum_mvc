package forum.controllers;

import forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class AuthorizedMenuController {
    private final UserService userService;

    @GetMapping("/auth")
    public String auth() {
        return "Authenticated success";
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logout success";
    }

    // Пример редиректа
    //("/") - указывает что запрос будет выглядеть вот так - /menu/
    @GetMapping("/")
    public ModelAndView homePage() {
        return new ModelAndView("redirect:/views/authorize/menu.html");
    }


    @PostMapping("/add")
    public String register(@RequestParam String login,
                           @RequestParam String password) {
        return userService.registrationUser(login, password);
    }


    //     Principal - очень сжатая инфа о юзере
    @GetMapping("/test")
    public String authPage(Principal principal) {
        userService.findByLogin(principal.getName());
        return "security: " + principal.getName();
    }

    @GetMapping("/info")
    public String authPage(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        String role = "";

        for (GrantedAuthority authority : user.getAuthorities()) {
            role = authority.getAuthority();
            break;
        }

        userService.findByLogin(username);

        return "security: " + username + ", Role: " + role;
    }
}
