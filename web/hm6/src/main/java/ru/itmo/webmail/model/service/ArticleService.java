package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class ArticleService {
//    private UserRepository userRepository = new UserRepositoryImpl();
    private ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> find(long userId) {
        return articleRepository.find(userId);
    }

    public void validate(Article article) throws ValidationException {
        if (article.getTitle().length() < 4) {
            throw new ValidationException("Title can't be shorter than 4");
        }
        if (article.getText().length() < 4) {
            throw new ValidationException("Text can't be shorter than 4");
        }
    }

    public void setHidden(long id, boolean hidden) {
        articleRepository.setHidden(id, hidden);
    }

    public Article checkUser(long id) {return articleRepository.findById(id);}
}
