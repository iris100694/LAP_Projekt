package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface RoomEquipmentRepository extends BaseRepository {

    Optional<ObservableList<RoomEquipment>> readAll() throws Exception;

    RoomEquipment add(Room room, Equipment equipment) throws Exception;

    void update(RoomEquipment roomEquipment) throws SQLException;

    void delete(RoomEquipment roomEquipment) throws SQLException;


}
