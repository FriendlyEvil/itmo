package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    List<Talk> findByUser(long id);
    List<Talk> findAll();
    void save(Talk user);
}
