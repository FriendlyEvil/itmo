package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EventRepository;
import ru.itmo.webmail.model.repository.TalkRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventRepositoryImpl extends RepositoryImpl implements EventRepository {

    //delete secret
    public void delete(String secret) {
        function(statement -> {
                    statement.setString(1, secret);
                    statement.executeQuery();
                    return statement.executeUpdate();
                }, "DELETE FROM emailConfirmation WHERE secret=?",
                "Can't delete confirm email.");
    }

    @Override
    public long confirmed(String secret) {
        return findBySecret(secret);
    }

    @Override
    public void setSecret(long id, String secret) {
        function(statement -> {
                    statement.setLong(1, id);
                    statement.setString(2, secret);
                    if (statement.executeUpdate() == 1) {
                        statement.getGeneratedKeys();
                    } else {
                        throw new RepositoryException("Can't save Secret.");
                    }
//                    return statement.executeUpdate();
                    return null;
                }, "INSERT INTO emailConfirmation (userId, secret,creationTime) VALUES (?, ?, NOW())",
                "Can't save Secret.");
    }

    private long findBySecret(String secret) {
        return function(statement -> {
                    statement.setString(1, secret);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getLong(1);
                        } else {
                            return Long.valueOf(-1);
                        }
                    }
                }, "SELECT userId FROM emailConfirmation WHERE secret=?",
                "Can't find User id by secret key.");
    }

}