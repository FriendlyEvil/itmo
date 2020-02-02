package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Tag;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.repository.NoticeRepository;
import ru.itmo.wm4.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final TagRepository tagRepository;

    public NoticeService(NoticeRepository noticeRepository, TagRepository tagRepository) {
        this.noticeRepository = noticeRepository;
        this.tagRepository = tagRepository;
    }

    public List<Notice> findByOrderByCreationTimeDesc() {
        return noticeRepository.findByOrderByCreationTimeDesc();
    }

    public void save(Notice notice, User user) {

        for (String s : notice.getTag().split("\\s")) {
            Tag tag = new Tag();
            tag.setName(s);
            if (tagRepository.findByName(s) == null) {
                tagRepository.save(tag);
            }
            notice.addTag(tagRepository.findByName(s));
        }
        user.addNotice(notice);
        noticeRepository.save(notice);
    }

    public Notice findById(Long userId) {
        return userId == null ? null : noticeRepository.findById(userId).orElse(null);
    }

}
