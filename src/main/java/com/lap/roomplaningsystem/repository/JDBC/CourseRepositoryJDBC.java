package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CourseRepositoryJDBC extends Repository implements CourseRepository {
    @Override
    public Optional<ObservableList<Course>> readAll() throws Exception {
        Connection connection = connect();

        String query = "{CALL courseListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        return createCourses(resultSet);
    }

    @Override
    public void add(Course course) throws SQLException {

    }

    @Override
    public void update(Course course) throws SQLException {

    }

    @Override
    public void delete(Course course) throws SQLException {

    }




    private Optional<ObservableList<Course>> createCourses(ResultSet resultSet) throws SQLException {
        ObservableList<Course> observableListCourse = FXCollections.observableArrayList();

        while (resultSet.next()) {

            Course course = new Course(resultSet.getInt("COURSEID"),
                    new Program(resultSet.getInt("PROGRAMID"),
                            resultSet.getString("DESCRIPTION")),
                    resultSet.getString("TITLE"),
                    resultSet.getInt("MEMBERS"),
                    resultSet.getDate("START"),
                    resultSet.getDate("END"));


            observableListCourse.add(course);
        }

        return Optional.of(observableListCourse);
    }


}
