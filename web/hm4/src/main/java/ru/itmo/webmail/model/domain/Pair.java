package ru.itmo.webmail.model.domain;

public class Pair {
    private News news;
    private String login;

    public Pair (News news, String login) {
        this.news = news;
        this.login = login;
    }

    public News getNews() {
        return news;
    }

    public String getLogin() {
        return login;
    }
}
