package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public interface CourseRepository extends BaseRepository{

    Optional<ObservableList<Course>> readAll() throws Exception;

    Course add(String description, Program program, Integer members, Date startDate, Date endDate) throws Exception;

    void update(Course course) throws SQLException;

    void delete(Course course) throws SQLException;


}
