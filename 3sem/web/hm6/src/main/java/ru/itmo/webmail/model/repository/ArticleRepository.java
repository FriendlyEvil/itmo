package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    List<Article> find(long userId);
    List<Article> findAll();
    void save(Article user);
    void setHidden(long id, boolean hidden);
    Article findById(long id);
}
