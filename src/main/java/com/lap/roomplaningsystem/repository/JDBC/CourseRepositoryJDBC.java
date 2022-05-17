package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
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
    public Course add(String description, Program program, Integer members, Date startDate, Date endDate) throws Exception {
        Connection connection = connect();

        String query = "INSERT INTO course (PROGRAMID, TITLE, MEMBERS, START, END) VALUES (?,?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, program.getProgramID());
        stmt.setString(2, description);
        stmt.setInt(3, members);
        stmt.setDate(4, startDate);
        stmt.setDate(5, endDate);
        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Course course = null;

        while(resultSet.next()){
            int courseID  = resultSet.getInt(1);
            course = new Course(courseID, program, description, members, startDate, endDate);
        }

        return course;
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
