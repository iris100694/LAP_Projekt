package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface EventRepository extends BaseRepository{

    Optional<ObservableList<Event>> readAll() throws Exception;

    Optional<ObservableList<Event>> filter(String filter) throws Exception;

    void add(Event event) throws SQLException;

    void update(Event event) throws SQLException;

    void delete(Event event) throws SQLException;
}
