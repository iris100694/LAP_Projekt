package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import javafx.collections.ObservableList;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public interface RoomRepository {

    Optional<ObservableList<Room>> readAll() throws Exception;

    Optional<ObservableList<Room>> filter(String stmt, boolean equipment) throws Exception;

    Room add(String description, Location location, String maxPersons, InputStream inputStream) throws SQLException;

    boolean update(Room room, InputStream inputStream) throws Exception;

    boolean delete(Room room) throws SQLException;


}
