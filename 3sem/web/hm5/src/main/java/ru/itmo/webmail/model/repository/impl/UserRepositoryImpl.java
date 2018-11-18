package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl extends RepositoryImpl implements UserRepository {

    private User getUserByStatement(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return toUser(statement.getMetaData(), resultSet);
            } else {
                return null;
            }
        }
    }

    @Override
    public User find(long userId) {
        return function(statement -> {
            statement.setLong(1, userId);
            return getUserByStatement(statement);
        }, "SELECT * FROM User WHERE id=?", "Can't find User by id.");
    }

    @Override
    public User findByLogin(String login) {
        return function(statement -> {
            statement.setString(1, login);
            return getUserByStatement(statement);
        }, "SELECT * FROM User WHERE login=?", "Can't find User by login.");
    }

    @Override
    public User findByEmail(String email) {
        return function(statement -> {
            statement.setString(1, email);
            return getUserByStatement(statement);
        }, "SELECT * FROM User WHERE email=?", "Can't find User by email.");
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return function(statement -> {
                    statement.setString(1, login);
                    statement.setString(2, passwordSha);
                    return getUserByStatement(statement);
                }, "SELECT * FROM User WHERE login=? AND passwordSha=?",
                "Can't find User by login and passwordSha.");
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return function(statement -> {
                    statement.setString(1, email);
                    statement.setString(2, passwordSha);
                    return getUserByStatement(statement);
                }, "SELECT * FROM User WHERE email=? AND passwordSha=?",
                "Can't find User by email and passwordSha.");
    }

    @Override
    public void enterOrExit(long userId, long event) {
        function(statement -> {
                    statement.setLong(1, userId);
                    statement.setLong(2, event);
                    if (statement.executeUpdate() == 1) {
                        statement.getGeneratedKeys();
                    } else {
                        throw new RepositoryException("Can't save event.");
                    }
                    return statement.executeUpdate();
                }, "INSERT INTO Event (userId, type, creationTime) VALUES (?, ?, NOW())",
                "Can't save event.");
    }

    @Override
    public void confirmed(long userId) {
        function(statement -> {
                    statement.setLong(1, userId);
                    statement.executeQuery();
                    return statement.executeUpdate();
                }, "UPDATE User SET confirmed=1 WHERE id=?",
                "Can't confirmed email.");
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        return function(statement -> {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            users.add(toUser(statement.getMetaData(), resultSet));
                        }
                        return users;
                    }
                }, "SELECT * FROM User ORDER BY id",
                "Can't find all users.");
    }

    @Override
    public void save(User user, String passwordSha) {
        function(statement -> {
                    statement.setString(1, user.getLogin());
                    statement.setString(2, passwordSha);
                    statement.setString(3, user.getEmail());
                    if (statement.executeUpdate() == 1) {
                        ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                        if (generatedIdResultSet.next()) {
                            user.setId(generatedIdResultSet.getLong(1));
                            user.setCreationTime(findCreationTime(user.getId()));
                        } else {
                            throw new RepositoryException("Can't find id of saved User.");
                        }
                    } else {
                        throw new RepositoryException("Can't save User.");
                    }
//                    return statement.executeUpdate();
                    return null;
                }, "INSERT INTO User (login, passwordSha, email, creationTime) VALUES (?, ?, ?, NOW())",
                "Can't save User.");
    }


    private Date findCreationTime(long userId) {
        return function(statement -> {
                    statement.setLong(1, userId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getTimestamp(1);
                        }
                    }
                    throw new RepositoryException("Can't find User.creationTime by id.");
                }, "SELECT creationTime FROM User WHERE id=?",
                "Can't find User.creationTime by id.");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                user.setId(resultSet.getLong(i));
            } else if ("login".equalsIgnoreCase(columnName)) {
                user.setLogin(resultSet.getString(i));
            } else if ("passwordSha".equalsIgnoreCase(columnName)) {
                // No operations.
            } else if ("email".equalsIgnoreCase(columnName)) {
                user.setEmail(resultSet.getString(i));
            } else if ("confirmed".equalsIgnoreCase(columnName)) {
                user.setConfirmed(resultSet.getBoolean(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                user.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return user;
    }
}