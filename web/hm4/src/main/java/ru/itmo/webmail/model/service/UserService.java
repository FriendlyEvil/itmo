package ru.itmo.webmail.model.service;

import com.google.common.hash.Hashing;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.UserRepository;
import ru.itmo.webmail.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String USER_PASSWORD_SALT = "dc3475f2b301851b";

    private UserRepository userRepository = new UserRepositoryImpl();

    public void validateRegistration(User user, String password, String passwordConfirmation, String email) throws ValidationException {
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

        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4");
        }
        if (email.indexOf('@') < 0 && email.indexOf('@') != email.lastIndexOf('@')) {
            throw new ValidationException("Email does not contain '@'");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Login is already in use");
        }
        if (password.length() > 32) {
            throw new ValidationException("Password can't be longer than 32");
        }
        if (!password.equals(passwordConfirmation)) {
            throw new ValidationException("Passwords do not match");
        }
    }

    public void register(User user, String password) {
        user.setPasswordSha1(sha256(password));
        userRepository.save(user);
    }

    public static String sha256(String st) {
        return Hashing.sha256().hashString(USER_PASSWORD_SALT + st,
                StandardCharsets.UTF_8).toString();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public long getId() {
        return userRepository.getId();
    }

    public long addId() {
        return userRepository.addId();
    }

    public User findById(long id) {
        return userRepository.findById(id);
    }
}
