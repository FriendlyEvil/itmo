package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.domain.Pair;
import ru.itmo.webmail.model.service.NewsService;
import ru.itmo.webmail.model.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexPage extends Page {
    private NewsService newsService = new NewsService();
    private UserService userService = new UserService();

    private void action(Map<String, Object> view) {
        view.put("newss", doPair(newsService.findAll()));
    }

    private void registrationDone(Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private void enterDone(Map<String, Object> view)  {
        view.put("message", "You have been enter");
    }

    private void logout(Map<String, Object> view)  {
        view.put("message", "You have been logout");
    }

    private List<Pair> doPair(List<News> news) {
        List<Pair> pairs = new ArrayList<>();
        for (News n : news) {
            pairs.add(new Pair(n, userService.findById(n.getUserId()).getLogin()));
        }
        return pairs;
    }
}
