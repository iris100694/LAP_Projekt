package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public interface EventRepository extends BaseRepository{

    Optional<ObservableList<Event>> readAll() throws Exception;

    Optional<ObservableList<Event>> filter(String filter) throws Exception;

    Event add(User creator, Room room, Course course, User coach, LocalDate date, LocalDateTime start, LocalDateTime end) throws Exception;

    void update(Event event) throws SQLException;

    void delete(Event event) throws SQLException;


    ObservableList<Event> seriesDaily(User creator, Room room, Course course, User coach, LocalDate startDate, LocalDate endDate, String startTime, String endTime);

    ObservableList<Event> seriesWeekly(User creator, Room room, Course course, User coach, LocalDate startDate, LocalDate endDate, String startTime, String endTime);

    ObservableList<Event> seriesMonthly(User creator, Room room, Course course, User coach, LocalDate startDate, LocalDate endDate, String startTime, String endTime);
}
