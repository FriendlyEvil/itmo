package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;

import java.util.List;

public interface EventRepository {
    long confirmed(String secret);
    void setSecret(long id, String secret);
    void delete(String secret);
}
