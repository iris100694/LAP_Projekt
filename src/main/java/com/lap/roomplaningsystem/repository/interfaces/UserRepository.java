package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends BaseRepository {

    Optional<ObservableList<User>> readAll() throws Exception;

    Optional<User> checkUsernamePw(String username, String password) throws SQLException;

    Optional<User> readUserByID(int id) throws SQLException;

    User add(String firstname, String lastname, String title, String username, String authorization, String password, Boolean trainer,
             Boolean textVisable, String phone, Boolean phoneVisable, String email, Boolean emailVisable, Boolean photoVisable,
             String text, InputStream inputStream) throws Exception;

    boolean update(User user, String password, InputStream inputStream) throws SQLException, IOException;

    void delete(User user) throws SQLException;

}

