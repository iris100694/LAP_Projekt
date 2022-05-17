package com.lap.roomplaningsystem.repository.interfaces;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface EquipmentRepository extends BaseRepository{

    Optional<ObservableList<Equipment>> readAll() throws Exception;

    Equipment add(String text) throws Exception;

    void update(Equipment equipment) throws SQLException;

    void delete(Equipment equipment) throws SQLException;


}
