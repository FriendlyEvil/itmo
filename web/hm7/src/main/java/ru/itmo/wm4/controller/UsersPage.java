package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.service.NoticeService;
import ru.itmo.wm4.service.UserService;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public String main(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @GetMapping(path = "/user/{id}")
    public String getUserPage(@PathVariable Long id, Model model) {
        model.addAttribute("user_data", userService.findById(id));
        return "UserPage";
    }

    @PostMapping(path = "/users/change/{id}")
    public String changeUserStatus(@PathVariable Long id) {
        userService.changeStatus(id);
        return "redirect:/users";
    }
}
