package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface RoomRepository {

    Optional<ObservableList<Room>> readAll() throws Exception;

    Optional<ObservableList<Room>> filter(String stmt, boolean equipment) throws Exception;

    void add(Room room) throws SQLException;

    void update(Room room) throws SQLException;

    void delete(Room room) throws SQLException;
}
