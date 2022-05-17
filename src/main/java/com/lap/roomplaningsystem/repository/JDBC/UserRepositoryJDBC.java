package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;

public class UserRepositoryJDBC extends Repository implements UserRepository {


    @Override
    public Optional<User> checkUsernamePw(String username, String password) throws SQLException {

        Connection connection = connect();

        String query = "{CALL loginStatement(?,?)}";
        CallableStatement stmt = connection.prepareCall(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet resultSet = stmt.executeQuery();
        Optional<User> user = createUser(resultSet);

        return user.isPresent()? user.get().isActive()? user : Optional.empty() : Optional.empty();
    }

    @Override
    public Optional<ObservableList<User>> readAll() throws SQLException {

        Connection connection = connect();

        String query = "{CALL userListStatement()}";
        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();
        return createUsers(resultSet);
    }

    @Override
    public Optional<User> readUserByID(int id) throws SQLException {

        Connection connection = connect();

        String query = "{CALL getUserStatement(?)}";
        CallableStatement stmt = connection.prepareCall(query);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        return createUser(resultSet);
    }

    @Override
    public User add(String firstname, String lastname, String title, String username, String authorization, String password, Boolean trainer, Boolean textVisable, String phone, Boolean phoneVisable, String email, Boolean emailVisable, Boolean photoVisable, String text, InputStream inputStream) throws Exception{
        byte[] photo= new byte[0];

        if (inputStream != null) {
            photo = inputStream.readAllBytes();
        }

        Connection connection = connect();

        String query = "INSERT INTO users(ACTIVE, TITLE, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, AUTHORIZATION," +
                "COACH, TEXT, TEXTVISABLE, PHONE, PHONEVISABLE, EMAIL, EMAILVISABLE, PHOTO, PHOTOVISABLE) VALUES " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setBoolean(1, true);
        stmt.setString(2, title);
        stmt.setString(3, firstname);
        stmt.setString(4, lastname);
        stmt.setString(5, username);
        stmt.setString(6, password);
        stmt.setString(7, authorization);
        stmt.setBoolean(8, trainer);
        stmt.setString(9, text);
        stmt.setBoolean(10, textVisable);
        stmt.setString(11, phone);
        stmt.setBoolean(12, phoneVisable);
        stmt.setString(13, email);
        stmt.setBoolean(14, emailVisable);
        stmt.setBlob(15, inputStream);
        stmt.setBoolean(16, photoVisable);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        User user = null;

        while(resultSet.next()){

            int userID  = resultSet.getInt(1);
            user = new User(userID, true, title, firstname, lastname, username, authorization, trainer, text, textVisable,
                    phone, phoneVisable, email, emailVisable, photo, photoVisable);
        }

        return user;


    }

//    @Override
//    public void add(User user){
//        Connection connection;
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


//    }

    @Override
    public void update(User user) throws SQLException {

    }

    @Override
    public void delete(User user) throws SQLException {

    }

    private Optional<User> createUser(ResultSet resultSet) throws SQLException {

        Optional<User> user = Optional.empty();
        while (resultSet.next()) {

            user = Optional.of(new User(resultSet.getInt("USERID"),
                    resultSet.getBoolean("ACTIVE"),
                    resultSet.getString("TITLE"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("USERNAME"),
                    resultSet.getString("AUTHORIZATION"),
                    resultSet.getBoolean("COACH"),
                    resultSet.getString("TEXT"),
                    resultSet.getBoolean("TEXTVISABLE"),
                    resultSet.getString("PHONE"),
                    resultSet.getBoolean("PHONEVISABLE"),
                    resultSet.getString("EMAIL"),
                    resultSet.getBoolean("EMAILVISABLE"),
                    resultSet.getBytes("PHOTO"),
                    resultSet.getBoolean("PHOTOVISABLE")));
        }
        return user;
    }

    private Optional<ObservableList<User>> createUsers(ResultSet resultSet) throws SQLException {

        ObservableList<User> users = FXCollections.observableArrayList();

        while (resultSet.next()) {

            User user = new User(resultSet.getInt("USERID"),
                    resultSet.getBoolean("ACTIVE"),
                    resultSet.getString("TITLE"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("USERNAME"),
                    resultSet.getString("AUTHORIZATION"),
                    resultSet.getBoolean("COACH"),
                    resultSet.getString("TEXT"),
                    resultSet.getBoolean("TEXTVISABLE"),
                    resultSet.getString("PHONE"),
                    resultSet.getBoolean("PHONEVISABLE"),
                    resultSet.getString("EMAIL"),
                    resultSet.getBoolean("EMAILVISABLE"),
                    resultSet.getBytes("PHOTO"),
                    resultSet.getBoolean("PHOTOVISABLE"));

            users.add(user);
        }

        return Optional.of(users);
    }
}
