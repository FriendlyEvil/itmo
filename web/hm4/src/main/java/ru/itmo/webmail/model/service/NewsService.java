package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.News;
import ru.itmo.webmail.model.repository.NewsRepository;
import ru.itmo.webmail.model.repository.impl.NewsRepositoryImpl;

import java.util.List;

public class NewsService {
    private NewsRepository newsRepository = new NewsRepositoryImpl();

    public void addNews(News news) {
        newsRepository.save(news);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }
}
