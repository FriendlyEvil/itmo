package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositoryImpl {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    protected  <T> T function(Wrap<T> wrap, String sql, String exception) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                return wrap.metod(statement);
            }
        } catch (SQLException e) {
            throw new RepositoryException(exception, e);
        }
    }

    interface Wrap<T> {
        T metod(PreparedStatement statement) throws SQLException;
    }
}