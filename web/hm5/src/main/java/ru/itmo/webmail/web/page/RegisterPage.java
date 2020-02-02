package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.EventService;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterPage extends Page {
    EventService eventService = new EventService();

    private void register(HttpServletRequest request, Map<String, Object> view) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        String password = request.getParameter("password");

        try {
            getUserService().validateRegistration(user, password);
        } catch (ValidationException e) {
            view.put("login", user.getLogin());
            view.put("password", password);
            view.put("email", user.getEmail());
            view.put("error", e.getMessage());
            return;
        }

        String secret = getUserService().register(user, password);
        request.getSession().setAttribute("confirm", "http://localhost:8080/confirm?secret="
                + secret);
        eventService.save(user.getId(), secret);
        throw new RedirectException("/index", "registrationDone");
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
