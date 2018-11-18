package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.NewsService;
import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Page {
    private UserService userService = new UserService();

    void before(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("loginHref", "#");
            view.put("loginValue", user.getLogin());
            view.put("logoutHref", "/logout");
            view.put("logoutValue", "Logout");

            view.put("news", "Add News");
        }
        view.put("userCount", userService.getId());
    }

    void after(Map<String, Object> view) {
    }

}
