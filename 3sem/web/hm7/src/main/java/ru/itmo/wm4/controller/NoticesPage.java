package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.service.NoticeService;

import javax.validation.Valid;

@Controller
public class NoticesPage extends Page {
    private final NoticeService noticeService;

    public NoticesPage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping(path = "/notices")
    public String registerGet(Model model) {
        model.addAttribute("registerForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping(path = "/notices")
    public String registerPost(@Valid @ModelAttribute("registerForm") NoticeCredentials registerForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.register(registerForm);

        return "redirect:/";
    }
}
