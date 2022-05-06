package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.RoomEquipment;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface RoomEquipmentRepository extends BaseRepository {

    Optional<ObservableList<RoomEquipment>> readAll() throws Exception;

    void add(RoomEquipment roomEquipment) throws SQLException;

    void update(RoomEquipment roomEquipment) throws SQLException;

    void delete(RoomEquipment roomEquipment) throws SQLException;
}
