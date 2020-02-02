package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MyArticlesPage extends Page {
    ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
       view.put("articles", getArticles(request, view));
    }

    private List<Article> getArticles(HttpServletRequest request, Map<String, Object> view) {
        List<Article> art = articleService.find((Long) request.getSession().getAttribute(USER_ID_SESSION_KEY));
        for (Article article : art)
            article.setLogin(getUserService().find(article.getUserId()).getLogin());
        return art;
    }

    private void changeHidden(HttpServletRequest request, Map<String, Object> view) {
        long articleId = Long.parseLong(request.getParameter("id"));
        long userId = (long) request.getSession().getAttribute(USER_ID_SESSION_KEY);

        Article article = articleService.checkUser(articleId);
        if (article != null && article.getUserId() == userId) {
            articleService.setHidden(articleId, !article.isHidden());
        }
    }
}
