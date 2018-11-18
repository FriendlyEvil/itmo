package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.service.NewsService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class NewsPage extends Page {
    private NewsService newsService = new NewsService();

    private void addNews(HttpServletRequest request) {
        String text = request.getParameter("text");
        User user = (User) request.getSession().getAttribute("user");

        News news = new News(user.getId(), text);
        newsService.addNews(news);

        throw new RedirectException("/index", "action");
    }

    private void action(Map<String, Object> view) {
//        view.put("news", newsService.findAll());
    }
}
