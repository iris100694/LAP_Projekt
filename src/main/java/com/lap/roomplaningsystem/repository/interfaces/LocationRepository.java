package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface LocationRepository extends BaseRepository{

    Optional<ObservableList<Location>> readAll() throws Exception;

    void add(Location location) throws SQLException;

    void update(Location location) throws SQLException;

    void delete(Location location) throws SQLException;
}
