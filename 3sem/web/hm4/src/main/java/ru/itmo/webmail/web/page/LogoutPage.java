package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutPage extends Page {
    private void logout(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().removeAttribute("user");

        throw new RedirectException("/index", "logout");
    }

    private void action() {
        // No operations.
    }
}
