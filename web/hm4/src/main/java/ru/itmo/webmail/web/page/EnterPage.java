package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private UserService userService = new UserService();

    private void enter(HttpServletRequest request, Map<String, Object> view) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.findByLogin(login);

        if (user == null) {
            view.put("error", "Incorrect login");
            return;
        }

        if (UserService.sha256(password).equals(user.getPasswordSha1())) {
            request.getSession().setAttribute("user", user);
        } else {
            view.put("error", "Incorrect password");
            return;
        }
        throw new RedirectException("/index", "enterDone");
    }

    private void action() {
        // No operations.
    }
}
