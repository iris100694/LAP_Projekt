package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User checkUsernamePw(String username, String password) throws SQLException;

    Optional<User> read(long id) throws SQLException;

    List<User> readAll() throws SQLException;

    void update(User user) throws SQLException;

    void delete(User user) throws SQLException;
}
