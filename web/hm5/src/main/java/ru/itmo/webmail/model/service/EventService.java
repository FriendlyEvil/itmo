package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class EventService {
    private EventRepository eventRepository = new EventRepositoryImpl();

    public void save(long id, String secret) {
        eventRepository.setSecret(id, secret);
    }

    public void delete(String secret) {
        eventRepository.delete(secret);
    }

    public long confirmed(String secret) {
        return eventRepository.confirmed(secret);
    }
}
