package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Role;
import ru.itmo.wm4.security.AnyRole;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {
    @AnyRole(Role.Name.ADMIN)
    @GetMapping(path = "/notice")
    public String noticeGet(Model model) {
        model.addAttribute("notice", new Notice());
        return "NoticePage";
    }


    @AnyRole(Role.Name.ADMIN)
    @PostMapping(path = "/notice")
    public String noticePost(@Valid @ModelAttribute("notice") Notice notice,
                             BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        getNoticeService().save(notice, getUser(httpSession));
        return "redirect:/notices";
    }

    @AnyRole({Role.Name.ADMIN, Role.Name.USER})
    @GetMapping(path = "/notice/{id}")
    public String noticeGet(@PathVariable Long id, Model model) {
        model.addAttribute("notice_data", getNoticeService().findById(id));
        return "NoticeCommentPage";
    }

    @AnyRole({Role.Name.ADMIN, Role.Name.USER})
    @PostMapping(path = "/notice/comment/{id}")
    public String noticeCommentPost(@Valid @ModelAttribute("comment") Comment comment,
                                    BindingResult bindingResult,
                                    @PathVariable Long id, HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("notice_data", getNoticeService().findById(id));
            return "NoticeCommentPage";
        }

        getCommentService().save(comment, getUser(httpSession), getNoticeService().findById(id));
        return "redirect:/notice/" + id;
    }
}
