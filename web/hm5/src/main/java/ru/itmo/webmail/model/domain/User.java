package ru.itmo.webmail.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private long id;
    private String login;
    private String email;
    private Date creationTime;
    private boolean Confirmed = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        Confirmed = confirmed;
    }
}
