package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface CourseRepository extends BaseRepository{

    Optional<ObservableList<Course>> readAll() throws Exception;

    void add(Course course) throws SQLException;

    void update(Course course) throws SQLException;

    void delete(Course course) throws SQLException;
}
