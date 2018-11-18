package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.random.Random;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.Events;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password) throws ValidationException {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (user.getEmail().indexOf('@') < 0 || user.getEmail().indexOf('@') != user.getEmail().lastIndexOf('@')) {
            throw new ValidationException("Email does not contain '@'");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
    }

    public String register(User user, String password) {
        String passwordSha = getPasswordSha(password);
        userRepository.save(user, passwordSha);

        //        eventRepository.setSecret(user.getId(), secret);

        return Random.getRandomString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void validateEnter(String login, String password) throws ValidationException {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login is required");
        }

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }

        User user = userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
        if (user == null) {
            user = userRepository.findByEmailAndPasswordSha(login, getPasswordSha(password));
            if (user == null)
                throw new ValidationException("Invalid login/email or password");
        }
        if (!user.isConfirmed()) {
            throw new ValidationException("Email don't confirmed");
        }
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + password,
                StandardCharsets.UTF_8).toString();
    }

    public User authorize(String login, String password) {
        User user = userRepository.findByLoginAndPasswordSha(login, getPasswordSha(password));
        if (user == null)
            user = userRepository.findByEmailAndPasswordSha(login, getPasswordSha(password));
        return user;
    }

    public User findByLoginOrEmail(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null)
            user = userRepository.findByEmail(login);
        return user;
    }

    public User find(long userId) {
        return userRepository.find(userId);
    }

    public void enterOrExit(long id, Events event) {
        userRepository.enterOrExit(id, event.ordinal() + 1);
    }

    public void confirmed(long id) {
        userRepository.confirmed(id);
    }
}
