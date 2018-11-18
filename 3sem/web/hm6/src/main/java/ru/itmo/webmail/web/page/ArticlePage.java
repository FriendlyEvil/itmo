package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private ArticleService articleService = new ArticleService();

    private Map<String, Object> save(HttpServletRequest request, Map<String, Object> view) {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        long userId = (long) request.getSession().getAttribute(USER_ID_SESSION_KEY);

        Article article = new Article();
        article.setUserId(userId);
        article.setTitle(title);
        article.setText(text);

        try {
            articleService.validate(article);
        } catch (ValidationException e) {
            view.put("success", false);
            view.put("error", e.getMessage());
            return view;
        }

        articleService.save(article);
        view.put("success", true);
        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
//        return a
    }
}
