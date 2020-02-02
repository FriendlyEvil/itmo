package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class TalkService {
    private TalkRepository talkRepository = new TalkRepositoryImpl();

    public void validate(Talk talk) throws ValidationException {
        if (talk.getText() == null || talk.getText().equals("")) {
            throw new ValidationException("Text is empty");
        }
        if (talk.getText().length() > 255) {
            throw new ValidationException("Text can't be longer than 8");
        }
    }

    public void save(Talk talk) {
        talkRepository.save(talk);
    }

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public List<Talk> find(long id) {
        return talkRepository.findByUser(id);
    }
}
