package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.TalkService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class TalkPage extends Page {
    private TalkService talkService = new TalkService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        long id = (long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        List<Talk> talks = talkService.find(id);
        for (Talk talk : talks) {
            talk.setTargetLogin(getUserService().find(talk.getTargetUserId()).getLogin());
            talk.setSourceLogin(getUserService().find(talk.getSourceUserId()).getLogin());
        }
        view.put("talks", talks);
    }

    private void saveTalk(HttpServletRequest request, Map<String, Object> view) {
        Talk talk = new Talk();

        long id = (long) request.getSession().getAttribute(USER_ID_SESSION_KEY);
        String toUser = request.getParameter("toUser");
        String text = request.getParameter("text");

        talk.setSourceUserId(id);
        talk.setText(text);

        User user = getUserService().findByLoginOrEmail(toUser);
        if (user == null) {
            view.put("error", "Incorrect target username");
            return;
        }

        talk.setTargetUserId(user.getId());

        try {
            talkService.validate(talk);
        } catch (ValidationException e) {
            view.put("error", e.getMessage());
            return;
        }
        talkService.save(talk);

        throw new RedirectException("/talk", "action");
    }
}
