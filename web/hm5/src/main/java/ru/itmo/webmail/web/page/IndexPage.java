package ru.itmo.webmail.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class IndexPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
        view.put("hrefemail", request.getSession().getAttribute("confirm"));

        request.getSession().removeAttribute("confirm");
    }

    private void confirmEmail(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You confirmed email");
    }
}
