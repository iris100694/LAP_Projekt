package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Program;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

public interface CourseRepository extends BaseRepository{

    Optional<ObservableList<Course>> readAll() throws Exception;

    Course add(String description, Program program, Integer members, Date startDate, Date endDate) throws Exception;

    boolean update(Course course) throws SQLException;

    boolean delete(Course course) throws SQLException;


}
