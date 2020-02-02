package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.form.UserCredentials;
import ru.itmo.wm4.repository.NoticeRepository;
import ru.itmo.wm4.repository.UserRepository;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public Notice register(NoticeCredentials registerForm) {
        Notice notice = new Notice();
        notice.setContent(registerForm.getContent());
        noticeRepository.save(notice);
        return notice;
    }

    public Notice findById(Long userId) {
        return userId == null ? null : noticeRepository.findById(userId).orElse(null);
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }
}
