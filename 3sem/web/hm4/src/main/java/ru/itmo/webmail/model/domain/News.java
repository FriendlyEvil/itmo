package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class News implements Serializable {
    private long userId;
    private String[] text;

    public News(long id, String text) {
        this.userId = id;
        this.text = text.split("\n");
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String[] getText() {
        return text;
    }
}
