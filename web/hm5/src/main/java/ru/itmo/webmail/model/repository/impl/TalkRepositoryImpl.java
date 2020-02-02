package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.TalkRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TalkRepositoryImpl extends RepositoryImpl implements TalkRepository {
    @Override
    public List<Talk> findByUser(long id) {
        List<Talk> talks = new ArrayList<>();
        return function(statement -> {
            statement.setLong(1, id);
            statement.setLong(2, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    talks.add(toTalk(statement.getMetaData(), resultSet));
                }
                return talks;
            }
        }, "SELECT * FROM Talk WHERE sourceUserId=? OR targetUserId=? ORDER BY creationTime DESC",
                "Can't find Talk by id.");
    }


    @Override
    public List<Talk> findAll() {
        List<Talk> talks = new ArrayList<>();
        return function(statement -> {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            talks.add(toTalk(statement.getMetaData(), resultSet));
                        }
                        return talks;
                    }
                }, "SELECT * FROM Talk ORDER BY id",
                "Can't find all talks.");
    }

    @Override
    public void save(Talk talk) {
        function(statement -> {
                    statement.setLong(1, talk.getSourceUserId());
                    statement.setLong(2, talk.getTargetUserId());
                    statement.setString(3, talk.getText());
                    if (statement.executeUpdate() == 1) {
                        ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                        if (generatedIdResultSet.next()) {
                            talk.setId(generatedIdResultSet.getLong(1));
//                            talk.setCreationTime(findCreationTime(talk.getId()));
                            talk.setCreationTime(generatedIdResultSet.getTimestamp(5));
                        } else {
                            throw new RepositoryException("Can't find id of saved Talk.");
                        }
                        return statement.executeUpdate();
                    } else {
                        throw new RepositoryException("Can't save Talk.");
                    }
                }, "INSERT INTO Talk (sourceUserId, targetUserId, `text`, creationTime) VALUES (?, ?, ?, NOW())",
                "Can't save Talk.");
    }


    private Date findCreationTime(long userId) {
        return function(statement -> {
                    statement.setLong(1, userId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getTimestamp(1);
                        }
                    }
                    throw new RepositoryException("Can't find Talk.creationTime by id.");
                }, "SELECT creationTime FROM Talk WHERE id=?",
                "Can't find Talk.creationTime by id.");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                talk.setId(resultSet.getLong(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                talk.setSourceUserId(resultSet.getLong(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                talk.setTargetUserId(resultSet.getLong(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                talk.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                talk.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'Talk." + columnName + "'.");
            }
        }
        return talk;
    }
}