package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class IndexPage extends Page {
    private ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private List<Article> getArticles(HttpServletRequest request, Map<String, Object> view) {
        List<Article> art = articleService.findAll();
        for (Article article : art)
            article.setLogin(getUserService().find(article.getUserId()).getLogin());
        return art;
    }
}
