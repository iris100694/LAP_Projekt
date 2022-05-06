package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends BaseRepository {

    Optional<ObservableList<User>> readAll() throws Exception;

    Optional<User> checkUsernamePw(String username, String password) throws SQLException;

    Optional<User> readUserByID(int id) throws SQLException;

    void add(User user) throws SQLException;

    void update(User user) throws SQLException;

    void delete(User user) throws SQLException;


}
