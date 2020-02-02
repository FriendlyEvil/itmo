package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.service.EventService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {
    EventService eventService = new EventService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");

        long id = eventService.confirmed(secret);
        if (id != -1) {
            getUserService().confirmed(id);
            eventService.delete(secret);
            throw new RedirectException("/index", "confirmEmail");
        }
        else
            throw new RedirectException("/index");
    }
}
