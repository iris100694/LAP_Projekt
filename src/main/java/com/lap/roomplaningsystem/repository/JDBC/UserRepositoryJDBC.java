package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.User;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBC extends Repository implements UserRepository {


    @Override
    public User checkUsernamePw(String username, String password) throws SQLException {
        return null;
    }

    public void add(User user){
        Connection connection;
//        try(PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM user")){
//            prepareStatement.setLong(1, user.getId());
//            prepareStatement.setString(2, user.getUsername());
//
//            InputStream inputStream = new ByteArrayInputStream(user.getPhoto());
//            prepareStatement.setBlob(3, inputStream);
//            prepareStatement.executeUpdate();
//
//            try(ResultSet resultSet = prepareStatement.getGeneratedKeys()){
//                while(resultSet.next()){
//                    long userID = resultSet.getLong("USERID");
//                }
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }


    }

    @Override
    public Optional<User> read(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<User> readAll() throws SQLException {
        return null;
    }

    @Override
    public void update(User user) throws SQLException {

    }

    @Override
    public void delete(User user) throws SQLException {

    }
}
